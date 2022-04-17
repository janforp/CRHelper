package com.janita.plugin.cr.remote.strategy.impl;

import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.remote.CrQuestionQueryRequest;
import com.janita.plugin.cr.remote.strategy.CrQuestionStorage;
import com.janita.plugin.util.CommonUtils;

import java.util.List;

/**
 * DbStorage
 *
 * @author zhucj
 * @since 20220324
 */
public class DbStorage implements CrQuestionStorage {

    @Override
    public void add(CrQuestion question) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.WARNING);
    }

    @Override
    public void update(CrQuestion question) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.WARNING);
    }

    @Override
    public List<CrQuestion> query(CrQuestionQueryRequest request) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.WARNING);
        throw new RuntimeException("暂时只支持本地");
    }
}
