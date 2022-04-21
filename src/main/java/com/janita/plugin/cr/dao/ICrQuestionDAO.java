package com.janita.plugin.cr.dao;

import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;

import java.util.List;

/**
 * ICrQuestionDAO
 *
 * @author zhucj
 * @since 20220324
 */
public interface ICrQuestionDAO {

    void insert(CrQuestion question);

    void update(CrQuestion question);

    void batchDelete(List<Integer> questionIdList);

    List<CrQuestion> query(CrQuestionQueryRequest request);
}