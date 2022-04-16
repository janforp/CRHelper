package com.janita.plugin.download;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.janita.plugin.download.download.Downloader;
import com.janita.plugin.download.download.MultiThreadFileDownloader;
import com.janita.plugin.download.support.MultiThreadDownloadProgressPrinter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class FastDownloadAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);

        DownloadDialog downloadDialog = new DownloadDialog();
        if (downloadDialog.showAndGet()) {
            // user pressed OK
            DownloadDialog.DialogInputHolder inputHolder = downloadDialog.getDialogInputHolder();
            try {
                startDownloadTask(project, inputHolder);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void startDownloadTask(Project project, DownloadDialog.DialogInputHolder inputHolder) throws IOException {

        MultiThreadDownloadProgressPrinter downloadProgressPrinter = new MultiThreadDownloadProgressPrinter(inputHolder.getThreadNum());

        // 实力化一个进度条实例
        ProgressManager progressManager = ProgressManager.getInstance();
        // 后台任务
        Task.Backgroundable background = new Task.Backgroundable(project, "File downloading") {

            /**
             * 临时存储已经下载字节数量的变量
             */
            private long alreadyDownloadLengthLastSecond;

            /**
             * 其实进度条就是在该run方法中不断的更新 progressIndicator 中的 setFraction 跟 setText，如果该方法结束了进度条就完成了
             * @param progressIndicator 进度指示器
             */
            @Override
            public void run(@NotNull ProgressIndicator progressIndicator) {
                // start your process
                while (true) {
                    long alreadyDownloadLength = downloadProgressPrinter.getAlreadyDownloadLength();
                    long contentLength = downloadProgressPrinter.getContentLength();
                    if (alreadyDownloadLength != 0 && alreadyDownloadLength >= contentLength) {
                        // Finished
                        progressIndicator.setFraction(1.0);
                        progressIndicator.setText("Download finished");
                        break;
                    }
                    updateProgressIndicatorPerSecond(progressIndicator, alreadyDownloadLengthLastSecond, contentLength, alreadyDownloadLength);
                    alreadyDownloadLengthLastSecond = alreadyDownloadLength;
                    // 每秒钟更新下载速度跟下载进度
                    sleep();
                }
            }
        };

        // 执行任务
        progressManager.run(background);

        CompletableFuture.runAsync(() -> {
            try {
                Downloader downloader = new MultiThreadFileDownloader(inputHolder.getThreadNum(), downloadProgressPrinter);
                downloader.download(inputHolder.getDownloadUrl(), inputHolder.getDirUrl());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).exceptionally(e -> {
            throw new RuntimeException(e);
        });
    }

    private void sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 实时更新下载速度跟进度条
     *
     * @param progressIndicator 指示器
     * @param alreadyDownloadLengthLastSecond 上次更新进度条速度的时候下载的字节数量，用于计算这一秒的速度
     * @param contentLength 总的长度
     * @param alreadyDownloadLengthThisSecond 本次更新进度条的时候已经下载的数量
     */
    private void updateProgressIndicatorPerSecond(ProgressIndicator progressIndicator, long alreadyDownloadLengthLastSecond, long contentLength, long alreadyDownloadLengthThisSecond) {
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
}