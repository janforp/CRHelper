package com.janita.plugin.cr;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.cr.dialog.CrCreateQuestionDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.util.CrQuestionUtils;
import com.janita.plugin.util.CommonUtils;

/**
 * CreateCrQuestionAction
 *
 * @author zhucj
 * @since 20220324
 */
public class CrCreateCrQuestionAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        boolean selection = CommonUtils.hasSelectAnyText(e);
        if (!selection) {
            CommonUtils.showNotification("请选择问题代码", MessageType.ERROR);
            return;
        }
        CrQuestion question = CrQuestionUtils.buildQuestion(e);
        CrCreateQuestionDialog dialog = new CrCreateQuestionDialog(project);
        CommonUtils.setToClipboard(question.getQuestionCode());
        dialog.open(question);
    }
}