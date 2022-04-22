package com.janita.plugin.cr.dao.impl;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.dao.ICrQuestionDAO;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;

import java.util.List;

/**
 * CrQuestionRestApiDAO
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionRestApiDAO implements ICrQuestionDAO {

    @Override
    public boolean insert(CrQuestion question) {
        return false;
    }

    @Override
    public boolean update(CrQuestion question) {
        return false;
    }

    @Override
    public boolean batchDelete(List<Integer> questionIdList) {
        return false;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        return null;
    }
}