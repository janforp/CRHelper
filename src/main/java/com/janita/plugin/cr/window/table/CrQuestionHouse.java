package com.janita.plugin.cr.window.table;

import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.util.CommonUtils;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.cr.service.CrQuestionService;
import org.fest.util.Lists;

import java.util.List;

/**
 * 这一层主要负责渲染问题列表
 *
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionHouse {

    public static void add(CrQuestion question) {
        boolean success = CrQuestionService.getInstance().insert(question);
        if (!success) {
            return;
        }
        CrQuestionTable.getCrQuestionList().add(question);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.addRow(raw);
    }

    public static void delete(int row, CrQuestion question) {
        boolean update = CrQuestionService.getInstance().batchDelete(Lists.newArrayList(question.getId()));
        if (!update) {
            return;
        }
        CrQuestionTable.getCrQuestionList().remove(row);
        question.setState(CrQuestionState.CLOSED.getDesc());
        CrQuestionTable.TABLE_MODEL.removeRow(row);
    }

    public static void update(Integer editIndex, CrQuestion question) {
        boolean update = CrQuestionService.getInstance().update(question);
        if (!update) {
            return;
        }
        CrQuestionTable.getCrQuestionList().set(editIndex, question);
        CrQuestionTable.TABLE_MODEL.removeRow(editIndex);
        String[] raw = CrQuestionTable.convertToRaw(question);
        CrQuestionTable.TABLE_MODEL.insertRow(editIndex, raw);
    }

    public static void rerenderTable(CrQuestionQueryRequest request) {
        Pair<Boolean, List<CrQuestion>> pair = CrQuestionService.getInstance().query(request);
        if (!pair.getLeft()) {
            return;
        }
        List<CrQuestion> questionList = pair.getRight();
        CrQuestionTable.getCrQuestionList().clear();
        CommonUtils.clearDefaultTableModel(CrQuestionTable.TABLE_MODEL);
        if (questionList == null || questionList.size() == 0) {
            return;
        }
        for (CrQuestion question : questionList) {
            CrQuestionTable.getCrQuestionList().add(question);
            String[] raw = CrQuestionTable.convertToRaw(question);
            CrQuestionTable.TABLE_MODEL.addRow(raw);
        }
    }
}