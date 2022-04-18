package com.janita.plugin.cr.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.domain.SelectTextOffLineHolder;
import com.janita.plugin.common.util.CommonUtils;
import com.janita.plugin.common.util.DateUtils;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.remote.CrQuestionHouse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionUtils {

    public static void saveQuestion(Boolean update, Integer editIndex, CrQuestion question) {
        if (update) {
            CrQuestionHouse.update(editIndex, question);
            return;
        }
        CrQuestionHouse.add(question);
    }

    /**
     * PersistentStateComponent
     * @param e
     * @return
     */
    public static CrQuestion buildQuestion(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Pair<Integer, Integer> startAndEndLine = CommonUtils.getStartAndEndLine(editor);
        SelectTextOffLineHolder holder = CommonUtils.getSelectTextOffLineHolder(editor);
        // 用户选择的文本
        String questionCode = CommonUtils.getSelectedText(e);
        // 当前文件的名称
        Pair<String, String> vcsPair = CommonUtils.getProjectNameAndBranchName(e);

        CrQuestion question = new CrQuestion();
        question.setProjectName(vcsPair.getLeft());
        question.setType(null);
        question.setLineFrom(startAndEndLine.getLeft());
        question.setLineTo(startAndEndLine.getRight());
        question.setClassName(CommonUtils.getClassName(e));
        question.setQuestionCode(questionCode);
        question.setBetterCode(questionCode);
        question.setDesc(null);
        question.setFromAccount(CommonUtils.getGitUser(e).getName());
        question.setToAccount(null);
        question.setGitBranchName(vcsPair.getRight());
        question.setSolveGitBranchName(null);
        question.setState("未解决");
        question.setCreateTime(DateUtils.getCurrentDateTime());
        question.setSolveTime(null);
        question.setDocumentStartLine(holder.getDocumentStartLine());
        question.setDocumentEndLine(holder.getDocumentEndLine());
        question.setLeadSelectionOffset(holder.getLeadSelectionOffset());
        question.setSelectionStartPositionLine(holder.getSelectionEndPositionLine());
        question.setSelectionStartPositionColumn(holder.getSelectionEndPositionColumn());
        question.setSelectionEndPositionLine(holder.getSelectionStartPositionLine());
        question.setSelectionEndPositionColumn(holder.getSelectionEndPositionColumn());
        return question;
    }

    /**
     * 解决了该问题
     */
    public static void solveQuestion(int index, CrQuestion question) {
        question.setState("已解决");
        question.setSolveTime(DateUtils.getCurrentDateTime());
        CrQuestionUtils.saveQuestion(true, index, question);
    }

    @SuppressWarnings("all")
    public static List<CrQuestion> processBeforeExport(List<CrQuestion> crQuestionList) {
        if (crQuestionList == null) {
            return new ArrayList<>(0);
        }

        List<CrQuestion> questionList = new ArrayList<>(crQuestionList.size());
        for (CrQuestion question : crQuestionList) {
            CrQuestion clone = new CrQuestion();
            clone.setProjectName(StringUtils.defaultIfBlank(question.getProjectName(), "无"));
            clone.setGitBranchName(StringUtils.defaultIfBlank(question.getGitBranchName(), "无"));
            clone.setClassName(StringUtils.defaultIfBlank(question.getClassName(), "无"));
            clone.setLineFrom(ObjectUtils.defaultIfNull(question.getLineFrom(), 0));
            clone.setLineTo(ObjectUtils.defaultIfNull(question.getLineTo(), 0));
            clone.setType(StringUtils.defaultIfBlank(question.getType(), "无"));
            clone.setToAccount(StringUtils.defaultIfBlank(question.getToAccount(), "无"));
            clone.setLevel(StringUtils.defaultIfBlank(question.getLevel(), "无"));
            clone.setState(StringUtils.defaultIfBlank(question.getState(), "无"));
            clone.setQuestionCode(setCodeMark(StringUtils.defaultIfBlank(question.getQuestionCode(), "无")));
            clone.setBetterCode(setCodeMark(StringUtils.defaultIfBlank(question.getBetterCode(), "无")));
            clone.setDesc(setCodeMark(StringUtils.defaultIfBlank(question.getDesc(), "无")));
            questionList.add(clone);
        }
        return questionList;
    }

    private static String setCodeMark(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }
        StringBuilder result = new StringBuilder();
        String[] split = text.split("\n");
        for (String str : split) {
            if (str.trim().length() == 0) {
                continue;
            }
            str = ">>" + str + "\n";
            result.append(str);
        }
        return result.toString().trim();
    }
}