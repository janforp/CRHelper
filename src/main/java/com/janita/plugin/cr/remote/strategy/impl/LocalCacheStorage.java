package com.janita.plugin.cr.remote.strategy.impl;

import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.persistent.CrQuestionPersistent;
import com.janita.plugin.cr.remote.CrQuestionQueryRequest;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorage;
import org.apache.commons.lang3.ObjectUtils;

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
public class LocalCacheStorage implements CrQuestionStorage {

    private static final AtomicLong ID_GEN = new AtomicLong(2);

    private static final CrQuestionPersistent CR_QUESTION_PERSISTENT = CrQuestionPersistent.getInstance();

    @Override
    public void add(CrQuestion question) {
        List<CrQuestion> questionListInCache = CR_QUESTION_PERSISTENT.getState();
        questionListInCache = ObjectUtils.defaultIfNull(questionListInCache, new ArrayList<>());

        Optional<Long> max = questionListInCache.stream().map(CrQuestion::getId).max(Long::compare);
        long maxId = max.isPresent() ? max.get() : 0;
        ID_GEN.set(maxId + 1);

        question.setId(ID_GEN.incrementAndGet());
        questionListInCache.add(question);
        CR_QUESTION_PERSISTENT.loadState(questionListInCache);
    }

    @Override
    public void update(CrQuestion question) {
        Long id = question.getId();
        List<CrQuestion> state = CR_QUESTION_PERSISTENT.getState();
        state = ObjectUtils.defaultIfNull(state, new ArrayList<>());
        Optional<CrQuestion> first = state.stream().filter(item -> item.getId().equals(id)).findFirst();
        if (first.isPresent()) {
            CrQuestion crQuestion = first.get();
            crQuestion.setQuestionCode(question.getQuestionCode());
        }
    }

    @Override
    public List<CrQuestion> query(CrQuestionQueryRequest request) {
        List<CrQuestion> questionListInCache = CR_QUESTION_PERSISTENT.getState();
        questionListInCache = ObjectUtils.defaultIfNull(questionListInCache, new ArrayList<>());
        Set<String> projectNameSet = request.getProjectNameSet();
        Set<String> projectNameSet2 = ObjectUtils.defaultIfNull(projectNameSet, new HashSet<>());
        Set<String> stateSet = request.getState();
        Set<String> stateSet2 = ObjectUtils.defaultIfNull(stateSet, new HashSet<>());
        return questionListInCache.stream().filter(question -> projectNameSet2.contains(question.getProjectName()) && stateSet2.contains(question.getState())).collect(Collectors.toList());
    }
}
