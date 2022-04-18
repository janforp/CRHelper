package com.janita.plugin.common.progress;

import com.janita.plugin.common.domain.CurrentProgress;

/**
 * ProgressTask
 *
 * @author zhucj
 * @since 20220324
 */
public interface ProgressTask {

    default long sleepSecond() {
        return 0;
    }

    long totalBit();

    long alreadyCompleteBit();

    long lastAlreadyCompleteBit();

    /**
     * 执行任务
     *
     * @param progress 任务进度
     */
    void process(CurrentProgress progress);
}
