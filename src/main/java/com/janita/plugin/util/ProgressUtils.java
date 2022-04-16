package com.janita.plugin.util;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.janita.plugin.common.domain.CurrentProgress;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * ProgressUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class ProgressUtils {

    public static void showProgress(Project project, String title, ProgressTask task, CurrentProgress progress) {
        ProgressManager progressManager = ProgressManager.getInstance();
        Task.Backgroundable background = new Task.Backgroundable(project, title) {

            // 临时存储已经下载字节数量的变量
            private long alreadyDownloadLengthLastSecond;

            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                while (true) {
                    if (progress.getAlready() != 0 && progress.getAlready() >= progress.getTotal()) {
                        // Finished
                        progressIndicator.setFraction(1.0);
                        progressIndicator.setText("Download finished");
                        break;
                    }
                    updateProgressIndicatorPerSecond(progressIndicator, alreadyDownloadLengthLastSecond, progress.getTotal(), progress.getAlready());
                    alreadyDownloadLengthLastSecond = progress.getTotal();
                    sleep();
                }

            }
        };
        progressManager.run(background);
        CompletableFuture.runAsync(task::process).exceptionally(e -> {
            throw new RuntimeException(e);
        });
    }

    /**
     * 实时更新下载速度跟进度条
     *
     * @param progressIndicator 指示器
     * @param alreadyDownloadLengthLastSecond 上次更新进度条速度的时候下载的字节数量，用于计算这一秒的速度
     * @param contentLength 总的长度
     * @param alreadyDownloadLengthThisSecond 本次更新进度条的时候已经下载的数量
     */
    public static void updateProgressIndicatorPerSecond(ProgressIndicator progressIndicator, long alreadyDownloadLengthLastSecond, long contentLength, long alreadyDownloadLengthThisSecond) {
        if (alreadyDownloadLengthThisSecond == 0 || contentLength == 0) {
            return;
        }
        long speed = alreadyDownloadLengthThisSecond - alreadyDownloadLengthLastSecond;
        double value = (double) alreadyDownloadLengthThisSecond / (double) contentLength;
        double fraction = Double.parseDouble(String.format("%.2f", value));
        progressIndicator.setFraction(fraction);
        String text = "already download " + fraction * 100 + "% ,speed: " + (speed / 1000) + "KB";
        progressIndicator.setText(text);
    }

    private static void sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
