package com.janita.plugin.db;

import com.intellij.ide.util.PropertiesComponent;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.enums.CrDataStorageEnum;
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
        String way = PropertiesComponent.getInstance().getValue(PersistentKeys.CR_DATA_STORAGE_WAY);
        CrDataStorageEnum storageWay = CrDataStorageEnum.getByDesc(way);
        if (storageWay == CrDataStorageEnum.MYSQL_DB) {
            return MySqlDatabaseServiceImpl.getInstance();
        }
        return SqliteDatabaseServiceImpl.getInstance();
    }
}