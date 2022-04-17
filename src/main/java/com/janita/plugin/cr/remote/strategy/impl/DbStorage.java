package com.janita.plugin.cr.remote.strategy.impl;

import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.common.domain.Pair;
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
    public boolean add(CrQuestion question) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.ERROR);
        return false;
    }

    @Override
    public boolean update(CrQuestion question) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.ERROR);
        return false;
    }

    @Override
    public Pair<Boolean, List<CrQuestion>> query(CrQuestionQueryRequest request) {
        CommonUtils.showNotification("抱歉，暂时只支持本地", MessageType.ERROR);
        return Pair.of(false, null);
    }
}
