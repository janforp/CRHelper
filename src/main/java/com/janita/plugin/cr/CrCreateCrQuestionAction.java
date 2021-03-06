package com.janita.plugin.cr;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.util.CommonUtils;
import com.janita.plugin.common.util.SingletonBeanFactory;
import com.janita.plugin.cr.dialog.CrQuestionEditDialog;
import com.janita.plugin.cr.dialog.CrQuestionSettingDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrCreateCrQuestionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        boolean clickOk = CrQuestionSettingDialog.checkStorageAndReturnIfClickOk();
        if (!clickOk) {
            // 用户点击了取消
            return;
        }
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        CrQuestion question = CrQuestion.newQuestion(e);
        Pair<Boolean, Set<String>> pair = SingletonBeanFactory.getCrQuestionService().queryAssignName(question.getProjectName());
        if (!pair.getLeft()) {
            CommonUtils.showNotification("CRHelper数据库配置不正确", MessageType.ERROR);
            return;
        }
        CrQuestionEditDialog crQuestionEditDialog = new CrQuestionEditDialog(project, question, pair.getRight(), true, null);
        crQuestionEditDialog.open();
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