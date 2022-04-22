package com.janita.plugin.cr.service;

import com.google.common.collect.Sets;
import com.intellij.openapi.application.ApplicationManager;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.dao.CrQuestionDAOFactory;
import com.janita.plugin.cr.dao.ICrQuestionDAO;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;

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

    private static final CrQuestionService CR_QUESTION_SERVICE = ApplicationManager.getApplication().getService(CrQuestionService.class);

    public static CrQuestionService getInstance() {
        return CR_QUESTION_SERVICE;
    }

    private CrQuestionService() {
    }

    public boolean insert(CrQuestion crQuestion) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDAOFactory.getDAO();
        return crQuestionDAO.insert(crQuestion);
    }

    public boolean update(CrQuestion crQuestion) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDAOFactory.getDAO();
        crQuestionDAO.update(crQuestion);
        return true;
    }

    public boolean batchDelete(List<Integer> questionIdList) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDAOFactory.getDAO();
        return crQuestionDAO.batchDelete(questionIdList);
    }

    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDAOFactory.getDAO();
        return crQuestionDAO.query(request);
    }

    public Set<String> queryAssignName(String projectName) {
        ICrQuestionDAO crQuestionDAO = CrQuestionDAOFactory.getDAO();
        CrQuestionQueryRequest request = new CrQuestionQueryRequest();
        request.setProjectName(projectName);
        Pair<Boolean, List<CrQuestion>> pair = crQuestionDAO.query(request);
        if (BooleanUtils.isNotTrue(pair.getLeft())) {
            return Sets.newHashSet(PluginConstant.PLEASE_MANUAL_ASSIGN);
        }
        List<CrQuestion> list = pair.getRight();
        if (CollectionUtils.isEmpty(list)) {
            return Sets.newHashSet(PluginConstant.PLEASE_MANUAL_ASSIGN);
        }
        Set<String> collect = list.stream().map(CrQuestion::getAssignTo).filter(Objects::nonNull).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(collect)) {
            return Sets.newHashSet(PluginConstant.PLEASE_MANUAL_ASSIGN);
        }
        return collect;
    }
}