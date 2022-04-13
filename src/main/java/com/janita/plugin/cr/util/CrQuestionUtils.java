package com.janita.plugin.cr.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionHouse;
import com.janita.plugin.util.CommonUtils;

import java.util.List;

/**
 * QuestionService
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionUtils {

    public static void saveQuestion(CrQuestion question) {
        CrQuestionHouse.add(question);

        List<CrQuestion> crQuestionList = CrQuestionHouse.getCrQuestionList();
        for (int i = 0; i < crQuestionList.size(); i++) {
            System.out.println((i + 1));
            System.out.println(crQuestionList.get(i));
            System.out.println("==================================================================");
        }
    }

    public static CrQuestion buildQuestion(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        Pair<Integer, Integer> startAndEndLine = CommonUtils.getStartAndEndLine(editor);
        // 用户选择的文本
        String questionCode = CommonUtils.getSelectedText(e);
        // 当前文件的名称
        Pair<String, String> vcsPair = CommonUtils.getProjectNameAndBranchName(e, project);

        CrQuestion question = new CrQuestion();
        question.setProjectName(vcsPair.getLeft());
        question.setType(null);
        question.setLineFrom(startAndEndLine.getLeft());
        question.setLineTo(startAndEndLine.getRight());
        question.setClassName(CommonUtils.getClassName(e));
        question.setQuestionCode(questionCode);
        question.setBetterCode(null);
        question.setDesc(null);
        question.setFromAccount(null);
        question.setToAccount(null);
        question.setGitBranchName(vcsPair.getRight());
        return question;
    }
}