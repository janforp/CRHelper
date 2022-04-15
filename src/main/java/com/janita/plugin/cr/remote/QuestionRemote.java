package com.janita.plugin.cr.remote;

import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.util.CommonUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * QuestionRemote
 * TODO
 *
 * @author zhucj
 * @since 20220324
 */
public class QuestionRemote {

    private static final AtomicLong id = new AtomicLong(2);

    public static void add(CrQuestion question) {
        question.setId(id.incrementAndGet());
    }

    public static void update(CrQuestion question) {

    }

    public static List<CrQuestion> query(CrQuestionQueryRequest request) {
        CommonUtils.showNotification("抱歉，暂时只支持本地，重启idea数据会丢失，请及时导出", MessageType.WARNING);
        return null;
    }
}