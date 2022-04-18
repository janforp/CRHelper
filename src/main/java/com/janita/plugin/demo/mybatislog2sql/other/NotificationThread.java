package com.janita.plugin.demo.mybatislog2sql.other;

import com.intellij.notification.Notification;
import com.intellij.notification.Notifications;

import java.util.concurrent.TimeUnit;

/**
 * 类说明：开启新线程弹出提示框
 *
 * @author zhucj
 * @since 2020/3/8 - 下午4:41
 */
public class NotificationThread extends Thread {

    private final Notification notification;

    private final int sleepTime;

    public NotificationThread(Notification notification, int sleepTime) {
        this.notification = notification;
        this.sleepTime = sleepTime;
    }

    public NotificationThread(Notification notification) {
        this(notification, 6);
    }

    @Override
    public void run() {
        Notifications.Bus.notify(this.notification);
        try {
            TimeUnit.SECONDS.sleep(this.sleepTime);
        } catch (InterruptedException e) {
            //empty
        }
        this.notification.expire();
    }
}
