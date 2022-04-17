package com.janita.plugin.cr.remote;

import com.janita.plugin.cr.dialog.CrFetchDataWayDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.util.CommonUtils;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionHouse {

    private static final List<CrQuestion> CR_QUESTION_LIST = new ArrayList<>();

    public static String[] HEAD = { "项目", "文件", "类型", "级别", "指派给", "提问者", "状态" };

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
        raw[1] = question.getClassName();
        raw[2] = question.getType();
        raw[3] = question.getLevel();
        raw[4] = question.getToAccount();
        raw[5] = question.getFromAccount();
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

    public static void refresh(CrQuestionQueryRequest request) {
        CrFetchDataWayDialog.doBeforeCr();
        List<CrQuestion> questionList = QuestionRemote.query(request);
        if (questionList == null || questionList.size() == 0) {
            return;
        }
        CR_QUESTION_LIST.clear();

        CommonUtils.clearDefaultTableModel(TABLE_MODEL);

        for (CrQuestion question : questionList) {
            CR_QUESTION_LIST.add(question);
            String[] raw = convertToRaw(question);
            TABLE_MODEL.addRow(raw);
        }
    }
}