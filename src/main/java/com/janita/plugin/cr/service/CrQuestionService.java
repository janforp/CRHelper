package com.janita.plugin.cr.service;

import com.intellij.openapi.application.ApplicationManager;
import com.janita.plugin.cr.dao.CrQuestionDAO;
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

    public static CrQuestionService getInstance() {
        return CR_QUESTION_SERVICE;
    }

    private static final CrQuestionService CR_QUESTION_SERVICE = ApplicationManager.getApplication().getService(CrQuestionService.class);

    private final CrQuestionDAO crQuestionDAO = new CrQuestionDAO();

    private CrQuestionService() {
    }

    public void batchInsert(List<CrQuestion> questionList) {
        crQuestionDAO.batchInsert(questionList);
    }

    public void batchUpdate(List<CrQuestion> questionList) {
        crQuestionDAO.batchUpdate(questionList);
    }

    public void batchDelete(List<Integer> questionIdList) {
        crQuestionDAO.batchDelete(questionIdList);
    }

    public List<CrQuestion> query(CrQuestionQueryRequest request) {
        return crQuestionDAO.query(request);
    }
}