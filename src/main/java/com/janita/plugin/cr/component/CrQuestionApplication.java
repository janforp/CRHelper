package com.janita.plugin.cr.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.common.util.SingletonBeanFactory;
import com.janita.plugin.cr.setting.CrQuestionSetting;
import com.janita.plugin.db.IDatabaseService;

/**
 * CrQuestionApplication
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class CrQuestionApplication implements ApplicationComponent {

    @Override
    public void initComponent() {
        CrQuestionSetting settingFromCache = CrQuestionSetting.getCrQuestionSettingFromCache();
        CrDataStorageEnum storageWay = settingFromCache.getStorageWay();
        if (storageWay == CrDataStorageEnum.MYSQL_DB || storageWay == CrDataStorageEnum.SQLITE_DB) {
            new Thread(new Task()).start();
        }
    }

    @Override
    public void disposeComponent() {
        SingletonBeanFactory.getDatabaseService().closeResource();
        ApplicationComponent.super.disposeComponent();
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            try {
                IDatabaseService database = SingletonBeanFactory.getDatabaseService();
                database.reInitConnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
