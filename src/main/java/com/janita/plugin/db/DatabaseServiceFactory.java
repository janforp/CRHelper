package com.janita.plugin.db;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.domain.CrDataStorage;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;
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
        CrDataStoragePersistent persistent = CrDataStoragePersistent.getInstance();
        CrDataStorage storage = persistent.getState();
        if (storage == null) {
            return SqliteDatabaseServiceImpl.getInstance();
        }
        CrDataStorageEnum storageWay = storage.getStorageWay();
        if (storageWay == CrDataStorageEnum.MYSQL_DB) {
            return MySqlDatabaseServiceImpl.getInstance();
        }
        return SqliteDatabaseServiceImpl.getInstance();
    }
}
