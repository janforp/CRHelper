package com.janita.plugin.cr.dao;

import com.janita.plugin.common.domain.Pair;
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

    boolean insert(CrQuestion question);

    boolean update(CrQuestion question);

    boolean batchDelete(List<Integer> questionIdList);

    Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request);
}