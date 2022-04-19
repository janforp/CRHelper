package com.janita.plugin.cr;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.janita.plugin.common.util.CommonUtils;
import com.janita.plugin.cr.dialog.CrCreateQuestionDialog;
import com.janita.plugin.cr.dialog.CrQuestionStorageDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.remote.QuestionRemote;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrCreateCrQuestionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        boolean clickOk = CrQuestionStorageDialog.checkStorageAndReturnIfClickOk(false);
        if (!clickOk) {
            // 用户点击了取消
            return;
        }
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        CrQuestion question = CrQuestion.newQuestion(e);
        Set<String> developerSet = QuestionRemote.queryDeveloperNameSet(question.getProjectName());
        CrCreateQuestionDialog dialog = new CrCreateQuestionDialog(project, developerSet);
        CommonUtils.setToClipboard(question.getQuestionCode());
        dialog.open(question);
    }

    /**
     * 通过复写Action 的 update 来控制Action是否可见
     *
     * @param e 事件
     */
    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        boolean selection = CommonUtils.hasSelectAnyText(e);
        // 用来设置该Action可用并且可见
        // e.getPresentation().setEnabledAndVisible(selection);
        // 用来设置该Action是否可用
        e.getPresentation().setEnabled(selection);
    }
}