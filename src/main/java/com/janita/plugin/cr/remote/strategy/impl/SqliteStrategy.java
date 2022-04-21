package com.janita.plugin.cr.remote.strategy.impl;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageStrategy;
import com.janita.plugin.cr.service.CrQuestionService;

import java.util.List;

/**
 * SqliteStrategy
 *
 * @author zhucj
 * @since 20220324
 */
public class SqliteStrategy implements CrQuestionStorageStrategy {

    @Override
    public boolean add(CrQuestion question) {
        CrQuestionService.getInstance().insert(question);
        return true;
    }

    @Override
    public boolean update(CrQuestion question) {
        CrQuestionService.getInstance().update(question);
        return true;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        List<CrQuestion> questionList = CrQuestionService.getInstance().query(request);
        return Pair.of(true, questionList);
    }
}