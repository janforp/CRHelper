package com.janita.plugin.cr.service;

import com.intellij.openapi.application.ApplicationManager;
import com.janita.plugin.cr.dao.CrQuestionDAOSqlite;
import com.janita.plugin.cr.dao.CrQuestionDAOFactory;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;

import java.util.List;

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

    public void insert(CrQuestion crQuestion) {
        CrQuestionDAOSqlite crQuestionDAO = CrQuestionDAOFactory.getDAO();
        crQuestionDAO.insert(crQuestion);
    }

    public void update(CrQuestion crQuestion) {
        CrQuestionDAOSqlite crQuestionDAO = CrQuestionDAOFactory.getDAO();
        crQuestionDAO.update(crQuestion);
    }

    public void batchDelete(List<Integer> questionIdList) {
        CrQuestionDAOSqlite crQuestionDAO = CrQuestionDAOFactory.getDAO();
        crQuestionDAO.batchDelete(questionIdList);
    }

    public List<CrQuestion> query(CrQuestionQueryRequest request) {
        CrQuestionDAOSqlite crQuestionDAO = CrQuestionDAOFactory.getDAO();
        return crQuestionDAO.query(request);
    }
}