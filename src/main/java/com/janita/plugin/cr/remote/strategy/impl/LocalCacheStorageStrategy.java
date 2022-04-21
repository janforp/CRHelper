package com.janita.plugin.cr.remote.strategy.impl;

import com.google.common.collect.Sets;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.cr.domain.CrDeveloper;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * LocalCacheStorage
 *
 * @author zhucj
 * @since 20220324
 */
public class LocalCacheStorageStrategy implements CrQuestionStorageStrategy {

    private static final AtomicInteger ID_GEN = new AtomicInteger(2);

    private static final CrQuestionPersistent CR_QUESTION_PERSISTENT = CrQuestionPersistent.getInstance();

    @Override
    public Set<CrDeveloper> queryDeveloper(String projectName) {
        CrDeveloper developer = new CrDeveloper("自己", "邮件", "自己");
        return Sets.newHashSet(developer);
    }

    @Override
    public boolean add(CrQuestion question) {
        List<CrQuestion> questionListInCache = CR_QUESTION_PERSISTENT.getState();
        questionListInCache = ObjectUtils.defaultIfNull(questionListInCache, new ArrayList<>());

        Optional<Integer> max = questionListInCache.stream().map(CrQuestion::getId).max(Long::compare);
        int maxId = max.orElse(0);
        ID_GEN.set(maxId + 1);

        question.setId(ID_GEN.incrementAndGet());
        questionListInCache.add(question);
        CR_QUESTION_PERSISTENT.loadState(questionListInCache);
        return true;
    }

    @Override
    public boolean update(CrQuestion question) {
        Integer id = question.getId();
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
        String projectName = request.getProjectName();
        Set<CrQuestionState> stateSet = request.getStateSet();
        Set<CrQuestionState> stateSet2 = ObjectUtils.defaultIfNull(stateSet, new HashSet<>());
        List<CrQuestion> crQuestions = questionListInCache.stream().filter(question -> question.getProjectName().equals(projectName) && stateSet2.contains(question.getState())).collect(Collectors.toList());
        return Pair.of(true, crQuestions);
    }
}