package com.janita.plugin.autgencode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.janita.plugin.autgencode.dialog.AutoCodeDialog;

/**
 * AutoCodeAction
 *
 * @author zhucj
 * @since 20220324
 */
public class AutoCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        AutoCodeDialog dialog = new AutoCodeDialog();
        dialog.setVisible(true);
    }
}