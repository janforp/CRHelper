package com.janita.plugin.cr.remote.strategy.impl;

import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.remote.CrQuestionQueryRequest;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorage;
import com.janita.plugin.rest.RestTemplateFactory;
import com.janita.plugin.util.CommonUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * RestServiceStorage
 *
 * @author zhucj
 * @since 20220324
 */
public class RestServiceStorage implements CrQuestionStorage {

    private static final RestTemplate restTemplate = RestTemplateFactory.getRestTemplate();

    @Override
    public void add(CrQuestion question) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.ERROR);
    }

    @Override
    public void update(CrQuestion question) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.ERROR);

    }

    @Override
    public List<CrQuestion> query(CrQuestionQueryRequest request) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.ERROR);
        throw new RuntimeException("暂时只支持本地");
    }
}
