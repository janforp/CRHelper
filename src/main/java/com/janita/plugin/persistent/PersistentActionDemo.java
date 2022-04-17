package com.janita.plugin.persistent;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.janita.plugin.persistent.data.DataSource;

/**
 * PersistentActionDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class PersistentActionDemo extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        TestPersistentStateComponent stateComponent = ApplicationManager.getApplication().getService(TestPersistentStateComponent.class);
        stateComponent.loadState(new DataSource("数据库地址", "数据库密码"));
    }
}