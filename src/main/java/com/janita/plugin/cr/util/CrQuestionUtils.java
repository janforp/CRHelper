package com.janita.plugin.cr.util;

import com.intellij.openapi.project.Project;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.util.DateUtils;
import com.janita.plugin.common.util.GitUtils;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.export.vo.CrQuestionExportVO;
import com.janita.plugin.cr.window.table.CrQuestionHouse;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionUtils {

    public static void solveQuestion(Project project, int index, CrQuestion question) {
        // 设置解决的版本
        String branchName = GitUtils.getBranchNameOrReturnNull(project, question.getFilePath(), question.getFileName());
        question.setState(CrQuestionState.SOLVED.getDesc());
        question.setSolveTime(DateUtils.getCurrentDateTime());
        question.setSolveGitBranchName(branchName);
        CrQuestionHouse.update(index, question, false);
    }

    public static List<CrQuestionExportVO> processBeforeExport(List<CrQuestion> crQuestionList) {
        if (crQuestionList == null) {
            return new ArrayList<>(0);
        }

        List<CrQuestionExportVO> questionList = new ArrayList<>(crQuestionList.size());
        for (CrQuestion question : crQuestionList) {
            CrQuestionExportVO clone = new CrQuestionExportVO();
            clone.setProjectName(StringUtils.defaultIfBlank(question.getProjectName(), "无"));
            clone.setCreateGitBranchName(StringUtils.defaultIfBlank(question.getCreateGitBranchName(), "无"));
            clone.setFileName(StringUtils.defaultIfBlank(question.getFileName(), "无"));
            clone.setType(StringUtils.defaultIfBlank(question.getType(), "无"));
            clone.setAssignTo(StringUtils.defaultIfBlank(question.getAssignTo(), "无"));
            clone.setLevel(StringUtils.defaultIfBlank(question.getLevel(), "无"));
            clone.setState(StringUtils.defaultIfBlank(question.getState(), "无"));
            clone.setQuestionCode(setCodeMark(StringUtils.defaultIfBlank(question.getQuestionCode(), "无")));
            clone.setBetterCode(setCodeMark(StringUtils.defaultIfBlank(question.getBetterCode(), "无")));
            clone.setDesc(setCodeMark(StringUtils.defaultIfBlank(question.getDescription(), "无")));
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