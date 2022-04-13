package com.janita.plugin.cr.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 问题仓库
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionHouse {

    public static List<CrQuestion> questionList = new ArrayList<>();

    public static void add(CrQuestion question) {
        questionList.add(question);
    }
}