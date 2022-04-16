package com.janita.plugin.demo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.janita.plugin.common.domain.CurrentProgress;
import com.janita.plugin.util.ProgressTask;
import com.janita.plugin.util.ProgressUtils;

/**
 * ProcessDemoAction
 *
 * @author zhucj
 * @since 20220324
 */
public class ProcessDemoAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        CurrentProgress progress = new CurrentProgress();
        ProgressUtils.showProgress(e.getProject(), "Request remote", new ProgressTask() {
            @Override
            public void process() {
                progress.setTotal(100);
                progress.setAlready(50);
                // 做事情
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                progress.setAlready(100);
            }
        }, progress);
    }
}
