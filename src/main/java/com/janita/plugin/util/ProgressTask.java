package com.janita.plugin.util;

import com.janita.plugin.common.domain.CurrentProgress;

/**
 * ProgressTask
 *
 * @author zhucj
 * @since 20220324
 */
public abstract class ProgressTask {

    public long sleepSecond() {
        return 0;
    }

    public long total() {
        return 100;
    }

    public long already() {
        return 50;
    }

    public long lastAlready() {
        return 10;
    }

    public void process(CurrentProgress progress) {
        progress.setTotal(total());
        progress.setAlready(already());
        doProcess();
        try {
            Thread.sleep(sleepSecond() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        progress.setAlready(total());
    }

    protected abstract void doProcess();
}
