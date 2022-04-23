package com.janita.plugin.db;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.setting.CrQuestionSetting;
import com.janita.plugin.db.impl.MySqlDatabaseServiceImpl;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;

/**
 * DatabaseServiceFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class DatabaseServiceFactory {

    public static IDatabaseService getDatabase() {
        CrDataStorageEnum storageWay = CrQuestionSetting.getStorageWayFromCache();
        if (storageWay == CrDataStorageEnum.MYSQL_DB) {
            return MySqlDatabaseServiceImpl.getInstance();
        }
        return SqliteDatabaseServiceImpl.getInstance();
    }
}