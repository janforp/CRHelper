package com.janita.plugin.demo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * 点击弹出对话框
 *
 * @author zhucj
 * @since 20220324
 */
public class ActionPopupDialog extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        DialogDemo dialog = new DialogDemo();
        dialog.pack();
        dialog.setVisible(true);
    }
}