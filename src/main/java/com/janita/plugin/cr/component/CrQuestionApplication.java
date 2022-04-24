package com.janita.plugin.cr.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.setting.CrQuestionSetting;
import com.janita.plugin.db.DatabaseServiceFactory;
import com.janita.plugin.db.IDatabaseService;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;

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
        SqliteDatabaseServiceImpl.getInstance().closeResource();
        ApplicationComponent.super.disposeComponent();
    }

    private static class Task implements Runnable {

        @Override
        public void run() {
            try {
                IDatabaseService database = DatabaseServiceFactory.getDatabase();
                database.reInitConnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
