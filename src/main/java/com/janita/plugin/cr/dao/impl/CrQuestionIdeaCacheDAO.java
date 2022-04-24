package com.janita.plugin.cr.dao.impl;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.util.SingletonBeanFactory;
import com.janita.plugin.cr.dao.ICrQuestionDAO;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrQuestionIdeaCacheDAO
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionIdeaCacheDAO implements ICrQuestionDAO {

    private CrQuestionIdeaCacheDAO() {
    }

    @Override
    public boolean insert(CrQuestion question) {
        List<CrQuestion> questionListInCache = SingletonBeanFactory.getCrQuestionDataPersistent().getState();
        questionListInCache = ObjectUtils.defaultIfNull(questionListInCache, new ArrayList<>());
        Optional<Integer> max = questionListInCache.stream().map(CrQuestion::getId).max(Long::compare);
        int maxId = max.orElse(0);
        question.setId(maxId + 1);
        questionListInCache.add(question);
        SingletonBeanFactory.getCrQuestionDataPersistent().loadState(questionListInCache);
        return true;
    }

    @Override
    public boolean update(CrQuestion question) {
        Integer id = question.getId();
        List<CrQuestion> crQuestionListInCache = SingletonBeanFactory.getCrQuestionDataPersistent().getState();
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
    public boolean batchDelete(List<Integer> questionIdList) {
        List<CrQuestion> questionList = SingletonBeanFactory.getCrQuestionDataPersistent().getState();
        if (CollectionUtils.isEmpty(questionList)) {
            return true;
        }
        questionList.removeIf(question -> questionIdList.contains(question.getId()));
        SingletonBeanFactory.getCrQuestionDataPersistent().loadState(questionList);
        return true;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        List<CrQuestion> questionListInCache = SingletonBeanFactory.getCrQuestionDataPersistent().getState();
        questionListInCache = ObjectUtils.defaultIfNull(questionListInCache, new ArrayList<>());
        String projectName = request.getProjectName();
        Set<CrQuestionState> stateSet = request.getStateSet();
        Set<CrQuestionState> stateSet2 = ObjectUtils.defaultIfNull(stateSet, new HashSet<>());
        List<CrQuestion> crQuestions = questionListInCache.stream().filter(question -> question.getProjectName().equals(projectName) && stateSet2.contains(CrQuestionState.getByDesc(question.getState()))).collect(Collectors.toList());
        return Pair.of(true, crQuestions);
    }
}
