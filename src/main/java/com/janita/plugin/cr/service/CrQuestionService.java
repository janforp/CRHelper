package com.janita.plugin.cr.service;

import com.google.common.collect.Sets;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.util.SingletonBeanFactory;
import com.janita.plugin.cr.dao.ICrQuestionDAO;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.db.impl.AbstractIDatabaseService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrQuestionService
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionService {

    private CrQuestionService() {
    }

    public boolean insert(CrQuestion crQuestion) {
        ICrQuestionDAO crQuestionDAO = SingletonBeanFactory.getCrQuestionDAO();
        return crQuestionDAO.insert(crQuestion);
    }

    public boolean update(CrQuestion crQuestion) {
        ICrQuestionDAO crQuestionDAO = SingletonBeanFactory.getCrQuestionDAO();
        return crQuestionDAO.update(crQuestion);
    }

    public boolean batchDelete(List<Integer> questionIdList) {
        ICrQuestionDAO crQuestionDAO = SingletonBeanFactory.getCrQuestionDAO();
        return crQuestionDAO.batchDelete(questionIdList);
    }

    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        ICrQuestionDAO crQuestionDAO = SingletonBeanFactory.getCrQuestionDAO();
        return crQuestionDAO.query(request);
    }

    public Pair<Boolean, Set<String>> queryAssignName(String projectName) {
        Connection connection = SingletonBeanFactory.getDatabaseService().getConnectDirectly();
        if (connection == AbstractIDatabaseService.INVALID_CONNECT) {
            return Pair.of(false, null);
        }
        ICrQuestionDAO crQuestionDAO = SingletonBeanFactory.getCrQuestionDAO();
        CrQuestionQueryRequest request = new CrQuestionQueryRequest();
        request.setProjectName(projectName);
        Pair<Boolean, List<CrQuestion>> pair = crQuestionDAO.query(request);
        if (BooleanUtils.isNotTrue(pair.getLeft())) {
            return Pair.of(true, Sets.newHashSet(PluginConstant.PLEASE_MANUAL_ASSIGN));
        }
        List<CrQuestion> list = pair.getRight();
        if (CollectionUtils.isEmpty(list)) {
            return Pair.of(true, Sets.newHashSet(PluginConstant.PLEASE_MANUAL_ASSIGN));
        }
        Set<String> collect = list.stream().map(CrQuestion::getAssignTo).filter(Objects::nonNull).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(collect)) {
            return Pair.of(true, Sets.newHashSet(PluginConstant.PLEASE_MANUAL_ASSIGN));
        }
        collect.remove(PluginConstant.PLEASE_MANUAL_ASSIGN);
        if (CollectionUtils.isEmpty(collect)) {
            return Pair.of(true, Sets.newHashSet(PluginConstant.PLEASE_MANUAL_ASSIGN));
        }
        return Pair.of(true, collect);
    }
}