package com.janita.plugin.cr.remote;

import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.persistent.CrDataStorageWayPersistent;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorage;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageFactory;

import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class QuestionRemote {

    public static void add(CrQuestion question) {
        CrQuestionStorage storage = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStorageWayPersistent.getPersistentData().getStorageWay());
        storage.add(question);
    }

    public static void update(CrQuestion question) {
        CrQuestionStorage storage = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStorageWayPersistent.getPersistentData().getStorageWay());
        storage.update(question);
    }

    public static List<CrQuestion> query(CrQuestionQueryRequest request) {
        CrQuestionStorage storage = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStorageWayPersistent.getPersistentData().getStorageWay());
        return storage.query(request);
    }
}