package com.janita.plugin.demo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.janita.plugin.progress.AbstractProgressTask;
import com.janita.plugin.progress.ProgressUtils;

/**
 * ProcessDemoAction
 *
 * @author zhucj
 * @since 20220324
 */
public class ProcessDemoAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        ProgressUtils.showProgress(e.getProject(), "Request remote", new AbstractProgressTask() {

            @Override
            public long sleepSecond() {
                return 10;
            }

            @Override
            protected void doProcess() {
                System.out.println("完成了");
            }
        });
    }
}
