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
        ProgressManager.getInstance().run(new Task.Backgroundable(project, "File downloading") {
            private long tmpAlreadyDownloadLength;

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
                    setProgressIndicator(progressIndicator, contentLength, alreadyDownloadLength);
                    sleep();
                }
            }

            private void setProgressIndicator(ProgressIndicator progressIndicator, long contentLength, long alreadyDownloadLength) {
                if (alreadyDownloadLength == 0 || contentLength == 0) {
                    return;
                }
                long speed = alreadyDownloadLength - tmpAlreadyDownloadLength;
                tmpAlreadyDownloadLength = alreadyDownloadLength;
                double value = (double) alreadyDownloadLength / (double) contentLength;
                double fraction = Double.parseDouble(String.format("%.2f", value));
                progressIndicator.setFraction(fraction);
                String text = "already download " + fraction * 100 + "% ,speed: " + (speed / 1000) + "KB";
                progressIndicator.setText(text);
            }
        });

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
}
