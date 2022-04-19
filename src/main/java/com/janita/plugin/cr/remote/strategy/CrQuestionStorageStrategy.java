package com.janita.plugin.cr.remote.strategy;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.domain.CrDeveloper;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CrQuestionStorage
 *
 * @author zhucj
 * @since 20220324
 */
public interface CrQuestionStorageStrategy {

    boolean add(CrQuestion question);

    boolean update(CrQuestion question);

    Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request);

    default Set<CrDeveloper> queryDeveloper(String projectName) {
        return new HashSet<>(0);
    }
}