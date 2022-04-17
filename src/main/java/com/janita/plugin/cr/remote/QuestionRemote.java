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

    private static final CrQuestionStorage STORAGE = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStorageWayPersistent.getPersistentData().getStorageWay());

    public static void add(CrQuestion question) {
        STORAGE.add(question);
    }

    public static void update(CrQuestion question) {
        STORAGE.update(question);
    }

    public static List<CrQuestion> query(CrQuestionQueryRequest request) {
        return STORAGE.query(request);
    }
}