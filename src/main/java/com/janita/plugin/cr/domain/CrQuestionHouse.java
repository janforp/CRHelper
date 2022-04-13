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

    private static final List<CrQuestion> CR_QUESTION_LIST = new ArrayList<>();

    public static void add(CrQuestion question) {
        CR_QUESTION_LIST.add(question);
    }

    public static List<CrQuestion> getCrQuestionList() {
        return CR_QUESTION_LIST;
    }
}