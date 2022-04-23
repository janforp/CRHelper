package com.janita.plugin.cr.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.setting.CrQuestionSetting;
import com.janita.plugin.db.IDatabaseService;
import com.janita.plugin.db.impl.MySqlDatabaseServiceImpl;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;

import java.sql.Connection;

/**
 * CrQuestionApplication
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionApplication implements ApplicationComponent {

    @Override
    public void initComponent() {
        CrQuestionSetting settingFromCache = CrQuestionSetting.getCrQuestionSettingFromCache();
        CrDataStorageEnum storageWay = settingFromCache.getStorageWay();
        if (storageWay == CrDataStorageEnum.MYSQL_DB) {
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
            IDatabaseService service = MySqlDatabaseServiceImpl.getInstance();
            Connection connection = service.getConnection();
            System.out.println(connection);
        }
    }
}
