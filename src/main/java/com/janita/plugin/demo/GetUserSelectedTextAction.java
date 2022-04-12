package com.janita.plugin.demo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * 拿到用户选择的文本
 *
 * @author zhucj
 * @since 20220324
 */
public class GetUserSelectedTextAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        //获取用户选择的内容
        //获取当前用户所在的编辑器对象
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        //通过编辑器得到用户选择的对象模型
        SelectionModel model = editor.getSelectionModel();
        //获取模型中的文本
        String selectedText = model.getSelectedText();
        System.out.println(selectedText);

        Project project = e.getData(PlatformDataKeys.PROJECT);
        Messages.showMessageDialog(project, "你选择的是：" + selectedText, "标题", Messages.getInformationIcon());
    }
}