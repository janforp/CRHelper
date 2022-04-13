package com.janita.plugin.cr.util;

import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionHouse;

/**
 * QuestionService
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionUtils {

    public static void saveQuestion(CrQuestion question) {
        CrQuestionHouse.add(question);
    }
}