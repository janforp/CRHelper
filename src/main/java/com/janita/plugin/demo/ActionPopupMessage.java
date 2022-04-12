package com.janita.plugin.demo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;

/**
 * 点击按钮弹出一条消息
 *
 * @author zhucj
 * @since 20220324
 */
public class ActionPopupMessage extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        JBPopup popup = JBPopupFactory.getInstance().createMessage("hello how are you");
        popup.showInBestPositionFor(e.getDataContext());
    }
}
