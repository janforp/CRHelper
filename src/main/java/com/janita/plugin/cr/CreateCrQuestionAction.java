package com.janita.plugin.cr;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;

/**
 * CreateCrQuestionAction
 *
 * @author zhucj
 * @since 20220324
 */
public class CreateCrQuestionAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        SelectionModel selectionModel = editor.getSelectionModel();

        // 用户选择的文本
        String questionCode = selectionModel.getSelectedText();
        // 当前文件的名称
        String className = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        System.out.println(questionCode);
        System.out.println(className);

        /*
         * 1.组装 CrQuestion 对象
         * 2.弹出对话框
         * 3.然后再输入其他信息
         * 4.点击保存就可以创建一个CR问题
         */
    }
}