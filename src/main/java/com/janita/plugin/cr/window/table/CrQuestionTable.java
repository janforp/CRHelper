package com.janita.plugin.cr.window.table;

import com.janita.plugin.cr.domain.CrQuestion;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * CrQuestionTable
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionTable {

    private static final List<CrQuestion> CR_QUESTION_LIST = new ArrayList<>();

    public static String[] HEAD = { "项目", "文件", "类型", "级别", "指派给", "提问者", "状态", "创建" };

    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(null, HEAD);

    public static List<CrQuestion> getCrQuestionList() {
        return CR_QUESTION_LIST;
    }

    public static String[] convertToRaw(CrQuestion question) {
        String[] raw = new String[8];
        raw[0] = question.getProjectName();
        raw[1] = question.getFileName();
        raw[2] = question.getType();
        raw[3] = question.getLevel();
        raw[4] = question.getAssignTo();
        raw[5] = question.getAssignFrom();
        raw[6] = question.getState();
        raw[7] = question.getCreateTime();
        return raw;
    }
}