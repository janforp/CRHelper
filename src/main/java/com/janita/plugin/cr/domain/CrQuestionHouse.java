package com.janita.plugin.cr.domain;

import com.janita.plugin.cr.remote.QuestionRemote;
import com.janita.plugin.util.CommonUtils;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 问题仓库
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionHouse {

    private static final List<CrQuestion> CR_QUESTION_LIST = new ArrayList<>();

    public static String[] HEAD = { "项目", "类型", "问题代码", "指派给", "提问者", "级别", "状态" };

    public static DefaultTableModel TABLE_MODEL = new DefaultTableModel(null, HEAD);

    public static void add(CrQuestion question) {
        QuestionRemote.add(question);
        CR_QUESTION_LIST.add(question);
        String[] raw = convertToRaw(question);
        TABLE_MODEL.addRow(raw);
    }

    public static List<CrQuestion> getCrQuestionList() {
        return CR_QUESTION_LIST;
    }

    private static String[] convertToRaw(CrQuestion question) {
        String[] raw = new String[7];
        raw[0] = question.getProjectName();
        raw[1] = question.getType();
        raw[2] = question.getQuestionCode();
        raw[3] = question.getToAccount();
        raw[4] = question.getFromAccount();
        raw[5] = question.getLevel();
        raw[6] = question.getState();
        return raw;
    }

    public static void delete(int row, CrQuestion question) {
        QuestionRemote.update(question);
        CR_QUESTION_LIST.remove(row);
        question.setState("已关闭");
        TABLE_MODEL.removeRow(row);
    }

    public static void update(Integer editIndex, CrQuestion question) {
        QuestionRemote.update(question);
        CR_QUESTION_LIST.set(editIndex, question);
        TABLE_MODEL.removeRow(editIndex);
        String[] raw = convertToRaw(question);
        TABLE_MODEL.insertRow(editIndex, raw);
    }

    public static void refresh(Set<String> projectNameSet) {
        List<CrQuestion> questionList = QuestionRemote.query(projectNameSet);
        CR_QUESTION_LIST.clear();

        CommonUtils.clearDefaultTableModel(TABLE_MODEL);

        for (CrQuestion question : questionList) {
            CR_QUESTION_LIST.add(question);
            String[] raw = convertToRaw(question);
            TABLE_MODEL.addRow(raw);
        }
    }
}