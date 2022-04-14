package com.janita.plugin.cr.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionHouse;
import com.janita.plugin.util.CommonUtils;
import com.janita.plugin.util.DateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * CrQuestionUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionUtils {

    public static void saveQuestion(Boolean update, Integer editIndex, CrQuestion question) {
        if (update) {
            CrQuestionHouse.update(editIndex, question);
            return;
        }
        CrQuestionHouse.add(question);
    }

    public static CrQuestion buildQuestion(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        Pair<Integer, Integer> startAndEndLine = CommonUtils.getStartAndEndLine(editor);
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
        question.setBetterCode(null);
        question.setDesc(null);
        question.setFromAccount(CommonUtils.getGitUser(e).getName());
        question.setToAccount(null);
        question.setGitBranchName(vcsPair.getRight());
        question.setSolveGitBranchName(null);
        question.setState("未解决");
        question.setCreateTime(DateUtils.getCurrentDateTime());
        question.setSolveTime(null);
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
            clone.setProjectName(StringUtils.defaultIfBlank(question.getProjectName(), StringUtils.EMPTY));
            clone.setGitBranchName(StringUtils.defaultIfBlank(question.getGitBranchName(), StringUtils.EMPTY));
            clone.setClassName(StringUtils.defaultIfBlank(question.getClassName(), StringUtils.EMPTY));
            clone.setLineFrom(ObjectUtils.defaultIfNull(question.getLineFrom(), 0));
            clone.setLineTo(ObjectUtils.defaultIfNull(question.getLineTo(), 0));
            clone.setType(StringUtils.defaultIfBlank(question.getType(), StringUtils.EMPTY));
            clone.setToAccount(StringUtils.defaultIfBlank(question.getToAccount(), StringUtils.EMPTY));
            clone.setLevel(StringUtils.defaultIfBlank(question.getLevel(), StringUtils.EMPTY));
            clone.setState(StringUtils.defaultIfBlank(question.getState(), StringUtils.EMPTY));
            clone.setQuestionCode(setCodeMark(StringUtils.defaultIfBlank(question.getQuestionCode(), StringUtils.EMPTY)));
            clone.setBetterCode(setCodeMark(StringUtils.defaultIfBlank(question.getBetterCode(), StringUtils.EMPTY)));
            clone.setDesc(StringUtils.defaultIfBlank(question.getDesc(), StringUtils.EMPTY));

            questionList.add(clone);
        }
        return questionList;
    }

    private static String setCodeMark(String text) {
        if (text == null || text.length() == 0) {
            return text;
        }
        String result = "";
        String[] split = text.split("\n");
        for (String str : split) {
            if (str.trim().length() == 0) {
                continue;
            }
            str = ">>" + str;
            result = result + "\n" + str;
        }
        return result;
    }

    public static void main(String[] args) {
        String a = "Object[] args = joinPoint.getArgs();\n"
                + "        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();\n"
                + "        String[] parameterNames = methodSignature.getParameterNames();\n"
                + "        for (int i = 0; i < parameterNames.length; i++) {\n"
                + "            if (StringUtils.equals(fieldName, parameterNames[i])) {\n"
                + "                Object arg = args[i];\n"
                + "                if (arg != null) {\n"
                + "                    return clazz.cast(arg);\n"
                + "                }\n"
                + "            }";

        String s = setCodeMark(a);
        System.out.println(s);
    }
}