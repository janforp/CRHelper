package com.janita.plugin.demo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.MessageType;
import com.janita.plugin.persistent.TestPersistentStateComponent;
import com.janita.plugin.persistent.data.DataSource;
import com.janita.plugin.util.CommonUtils;

import java.awt.*;

/**
 * 点击弹出对话框
 *
 * @author zhucj
 * @since 20220324
 */
public class ActionPopupDialog extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        TestPersistentStateComponent stateComponent = ApplicationManager.getApplication().getComponent(TestPersistentStateComponent.class);
        DataSource state = stateComponent.getState();
        CommonUtils.showNotification(state.toString(), MessageType.WARNING);
        DialogDemo dialog = new DialogDemo();
        dialog.pack();
        dialog.setVisible(true);
    }
}