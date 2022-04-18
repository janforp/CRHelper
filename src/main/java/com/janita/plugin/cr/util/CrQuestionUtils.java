package com.janita.plugin.cr.util;

import com.janita.plugin.common.util.DateUtils;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.window.table.CrQuestionHouse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionUtils {

    public static void solveQuestion(int index, CrQuestion question) {
        question.setState("已解决");
        question.setSolveTime(DateUtils.getCurrentDateTime());
        CrQuestionHouse.update(index, question);
    }

    @SuppressWarnings("all")
    public static List<CrQuestion> processBeforeExport(List<CrQuestion> crQuestionList) {
        if (crQuestionList == null) {
            return new ArrayList<>(0);
        }

        List<CrQuestion> questionList = new ArrayList<>(crQuestionList.size());
        for (CrQuestion question : crQuestionList) {
            CrQuestion clone = CrQuestion.newCrQuestion();
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