package com.janita.plugin.cr.domain;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.janita.plugin.common.domain.SelectFileInfo;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.util.CommonUtils;
import com.janita.plugin.common.util.CompatibleUtils;
import com.janita.plugin.common.util.DateUtils;
import com.janita.plugin.common.util.GitUtils;
import lombok.Data;
import lombok.ToString;

/**
 * @author zhucj
 * @since 20220415
 */
@Data
@ToString
public class CrQuestion {

    /**
     * id
     */
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 文件名称/路径
     */
    private String filePath;

    /**
     * 名称
     */
    private String fileName;

    /**
     * 语言
     */
    private String language;

    /**
     * 问题类型
     */
    private String type;

    /**
     * 该问题的级别,类似复杂程度,数字越大问题越复杂
     */
    private String level;

    /**
     * 状态
     * 未解决,已解决,重复问题,已关闭
     */
    private CrQuestionState state;

    /**
     * 提出问题的人
     */
    private String assignFrom;

    /**
     * 接收问题的人
     */
    private String assignTo;

    /**
     * 问题代码
     */
    private String questionCode;

    /**
     * 建议写法
     */
    private String betterCode;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建git分支名称
     */
    private String createGitBranchName;

    /**
     * 解决git分支名称
     */
    private String solveGitBranchName;

    /**
     * 提问时间
     */
    private String createTime;

    /**
     * 解决时间
     */
    private String solveTime;

    /**
     * 起始
     */
    private Integer offsetStart;

    /**
     * 结束行
     */
    private Integer offsetEnd;

    private CrQuestion() {
        // empty
    }

    public static CrQuestion newQuestion(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        SelectFileInfo holder = CommonUtils.getSelectFileInfo(e);

        CrQuestion question = new CrQuestion();
        question.setProjectName(CompatibleUtils.getProjectNameFromGitFirstThenFromLocal(project, virtualFile));
        question.setFilePath(holder.getFilePath());
        question.setFileName(holder.getFileName());
        question.setLanguage(holder.getLanguage());
        question.setState(CrQuestionState.UNSOLVED);
        question.setAssignFrom(GitUtils.getGitUserName(project));
        question.setQuestionCode(holder.getSelectedText());
        question.setBetterCode(holder.getSelectedText());
        question.setCreateGitBranchName(CompatibleUtils.getBranchNameFromGitFirstThenFromLocal(project, virtualFile));
        question.setCreateTime(DateUtils.getCurrentDateTime());

        question.setOffsetStart(holder.getOffsetStart());
        question.setOffsetEnd(holder.getOffsetEnd());
        return question;
    }
}