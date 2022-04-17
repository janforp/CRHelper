package com.janita.plugin.cr.remote.strategy;

import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.remote.CrQuestionQueryRequest;

import java.util.List;

/**
 * CrQuestionStorage
 *
 * @author zhucj
 * @since 20220324
 */
public interface CrQuestionStorage {

    void add(CrQuestion question);

    void update(CrQuestion question);

    List<CrQuestion> query(CrQuestionQueryRequest request);
}
