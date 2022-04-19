package com.janita.plugin.cr.remote.strategy.impl;

import com.google.common.collect.Sets;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.cr.domain.CrDeveloper;
import com.janita.plugin.cr.domain.CrProjectInfo;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.cr.persistent.CrQuestionPersistent;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageStrategy;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * LocalCacheStorage
 *
 * @author zhucj
 * @since 20220324
 */
public class LocalCacheStorageStrategy implements CrQuestionStorageStrategy {

    private static final AtomicLong ID_GEN = new AtomicLong(2);

    private static final CrQuestionPersistent CR_QUESTION_PERSISTENT = CrQuestionPersistent.getInstance();

    @Override
    public List<CrProjectInfo> queryProject(String gitUserName) {
        List<CrQuestion> questionList = CrQuestionPersistent.getInstance().getState();
        if (questionList == null || questionList.size() == 0) {
            return new ArrayList<>(0);
        }
        List<CrProjectInfo> infoList = new ArrayList<>();
        Set<String> nameSet = questionList.stream().map(CrQuestion::getProjectName).collect(Collectors.toSet());
        for (String projectName : nameSet) {
            CrProjectInfo projectInfo = new CrProjectInfo(projectName, Sets.newHashSet(new CrDeveloper(null, null, "自己")));
            infoList.add(projectInfo);
        }
        return infoList;
    }

    @Override
    public boolean add(CrQuestion question) {
        List<CrQuestion> questionListInCache = CR_QUESTION_PERSISTENT.getState();
        questionListInCache = ObjectUtils.defaultIfNull(questionListInCache, new ArrayList<>());

        Optional<Long> max = questionListInCache.stream().map(CrQuestion::getId).max(Long::compare);
        long maxId = max.isPresent() ? max.get() : 0;
        ID_GEN.set(maxId + 1);

        question.setId(ID_GEN.incrementAndGet());
        questionListInCache.add(question);
        CR_QUESTION_PERSISTENT.loadState(questionListInCache);
        return true;
    }

    @Override
    public boolean update(CrQuestion question) {
        Long id = question.getId();
        List<CrQuestion> crQuestionListInCache = CR_QUESTION_PERSISTENT.getState();
        crQuestionListInCache = ObjectUtils.defaultIfNull(crQuestionListInCache, new ArrayList<>());
        Optional<CrQuestion> questionInCache = crQuestionListInCache.stream().filter(item -> item.getId().equals(id)).findFirst();
        if (!questionInCache.isPresent()) {
            return true;
        }
        CrQuestion crQuestionInCache = questionInCache.get();
        BeanUtils.copyProperties(question, crQuestionInCache);
        return true;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        List<CrQuestion> questionListInCache = CR_QUESTION_PERSISTENT.getState();
        questionListInCache = ObjectUtils.defaultIfNull(questionListInCache, new ArrayList<>());
        Set<String> projectNameSet = request.getProjectNameSet();
        Set<String> projectNameSet2 = ObjectUtils.defaultIfNull(projectNameSet, new HashSet<>());
        Set<CrQuestionState> stateSet = request.getStateSet();
        Set<CrQuestionState> stateSet2 = ObjectUtils.defaultIfNull(stateSet, new HashSet<>());
        List<CrQuestion> crQuestions = questionListInCache.stream().filter(question -> projectNameSet2.contains(question.getProjectName()) && stateSet2.contains(question.getState())).collect(Collectors.toList());
        return Pair.of(true, crQuestions);
    }
}