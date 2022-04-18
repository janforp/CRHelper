package com.janita.plugin.cr.domain;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.domain.SelectTextOffLineHolder;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.util.CommonUtils;
import com.janita.plugin.common.util.DateUtils;
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
     * 该问题的级别,类似复杂程度,数字越大问题越复杂
     */
    private String level;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 问题类型
     */
    private String type;

    /**
     * 起始行
     */
    private Integer lineFrom;

    /**
     * 结束行
     */
    private Integer lineTo;

    /**
     * 类名称
     */
    private String className;

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
    private String desc;

    /**
     * 提出问题的人
     */
    private String fromAccount;

    /**
     * 接收问题的人
     */
    private String toAccount;

    /**
     * git分支名称
     */
    private String gitBranchName;

    /**
     * 解决版本
     */
    private String solveGitBranchName;

    /**
     * 状态
     * 未解决,已解决,重复问题,已关闭
     */
    private CrQuestionState state;

    /**
     * 提问时间
     */
    private String createTime;

    /**
     * 解决时间
     */
    private String solveTime;

    /**
     * 开始行
     */
    private int documentStartLine;

    /**
     * 开始行
     */
    private int documentEndLine;

    /**
     * 开始offset
     *
     * @see SelectionModel#getLeadSelectionOffset()
     */
    private int leadSelectionOffset;

    /**
     * @see SelectionModel#getSelectionStartPosition()
     * @see VisualPosition#getLine()
     */
    private int selectionStartPositionLine;

    /**
     * @see SelectionModel#getSelectionStartPosition()
     * @see VisualPosition#getColumn()
     */
    private int selectionStartPositionColumn;

    /**
     * @see SelectionModel#getSelectionEndPosition()
     * @see VisualPosition#getLine()
     */
    private int selectionEndPositionLine;

    /**
     * @see SelectionModel#getSelectionEndPosition()
     * @see VisualPosition#getColumn()
     */
    private int selectionEndPositionColumn;

    private CrQuestion() {
        // empty
    }

    public static CrQuestion newQuestion(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Pair<Integer, Integer> startAndEndLine = CommonUtils.getStartAndEndLine(editor);
        SelectTextOffLineHolder holder = CommonUtils.getSelectTextOffLineHolder(editor);
        // 用户选择的文本
        String questionCode = CommonUtils.getSelectedText(e);
        // 当前文件的名称
        Pair<String, String> vcsPair = CommonUtils.getProjectNameAndBranchName(e);

        CrQuestion question = new CrQuestion();
        question.setProjectName(vcsPair.getLeft());
        question.setLineFrom(startAndEndLine.getLeft());
        question.setLineTo(startAndEndLine.getRight());
        question.setClassName(CommonUtils.getClassName(e));
        question.setQuestionCode(questionCode);
        question.setBetterCode(questionCode);
        question.setFromAccount(CommonUtils.getGitUser(e).getName());
        question.setGitBranchName(vcsPair.getRight());
        question.setState(CrQuestionState.UNSOLVED);
        question.setCreateTime(DateUtils.getCurrentDateTime());
        question.setDocumentStartLine(holder.getDocumentStartLine());
        question.setDocumentEndLine(holder.getDocumentEndLine());
        question.setLeadSelectionOffset(holder.getLeadSelectionOffset());
        question.setSelectionStartPositionLine(holder.getSelectionEndPositionLine());
        question.setSelectionStartPositionColumn(holder.getSelectionEndPositionColumn());
        question.setSelectionEndPositionLine(holder.getSelectionStartPositionLine());
        question.setSelectionEndPositionColumn(holder.getSelectionEndPositionColumn());
        return question;
    }
}