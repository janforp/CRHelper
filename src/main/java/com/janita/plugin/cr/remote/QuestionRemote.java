package com.janita.plugin.cr.remote;

import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.persistent.CrDataStorageWayPersistent;
import com.janita.plugin.cr.persistent.CrQuestionPersistent;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorage;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorageFactory;
import com.janita.plugin.rest.RestTemplateFactory;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class QuestionRemote {

    private static final RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();

    private static final CrDataStorageWayPersistent STORAGE_WAY_PERSISTENT = CrDataStorageWayPersistent.getInstance();

    private static final CrQuestionPersistent CR_QUESTION_PERSISTENT = CrQuestionPersistent.getInstance();

    public static void add(CrQuestion question) {
        CrQuestionStorage storage = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStorageWayPersistent.getPersistentData().getWayName());
        storage.add(question);
    }

    public static void update(CrQuestion question) {
        CrQuestionStorage storage = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStorageWayPersistent.getPersistentData().getWayName());
        storage.update(question);
    }

    public static List<CrQuestion> query(CrQuestionQueryRequest request) {
        CrQuestionStorage storage = CrQuestionStorageFactory.getCrQuestionStorage(CrDataStorageWayPersistent.getPersistentData().getWayName());
        return storage.query(request);
    }
}