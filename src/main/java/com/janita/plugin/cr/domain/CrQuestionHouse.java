package com.janita.plugin.cr.domain;

import javax.swing.table.DefaultTableModel;
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

    public static String[] HEAD = { "项目", "类型", "问题代码", "指派者", "提问者" };

    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(null, HEAD);

    public static void add(CrQuestion question) {
        CR_QUESTION_LIST.add(question);
        String[] raw = convertToRaw(question);
        TABLE_MODEL.addRow(raw);
    }

    public static List<CrQuestion> getCrQuestionList() {
        return CR_QUESTION_LIST;
    }

    private static String[] convertToRaw(CrQuestion question) {
        String[] raw = new String[5];
        raw[0] = question.getProjectName();
        raw[1] = question.getType();
        raw[2] = question.getQuestionCode();
        raw[3] = question.getToAccount();
        raw[4] = question.getFromAccount();
        return raw;
    }

    public static void delete(int row) {
        CR_QUESTION_LIST.remove(row);
        TABLE_MODEL.removeRow(row);
    }
}