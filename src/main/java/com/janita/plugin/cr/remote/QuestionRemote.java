package com.janita.plugin.cr.remote;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageFactory;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageStrategy;

import java.util.List;

/**
 * 这一层主要负责存储数据
 *
 * @author zhucj
 * @since 20220415
 */
public class QuestionRemote {

    public static boolean add(CrQuestion question) {
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        return strategy.add(question);
    }

    public static boolean update(CrQuestion question) {
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        return strategy.update(question);
    }

    public static Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        CrQuestionStorageStrategy strategy = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStoragePersistent.getPersistentData().getStorageWay());
        return strategy.query(request);
    }
}