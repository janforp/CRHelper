package com.janita.plugin.cr.remote.strategy;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.remote.strategy.impl.DbStorage;
import com.janita.plugin.cr.remote.strategy.impl.LocalCacheStorage;
import com.janita.plugin.cr.remote.strategy.impl.RestServiceStorage;

/**
 * CrQuestionStorageFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionStorageFactory {

    public static CrQuestionStorage getCrQuestionStorage(CrDataStorageEnum storageWay) {
        if (CrDataStorageEnum.LOCAL_CACHE == storageWay) {
            return new LocalCacheStorage();
        }
        if (CrDataStorageEnum.DB == storageWay) {
            return new DbStorage();
        }
        if (CrDataStorageEnum.REST_API == storageWay) {
            return new RestServiceStorage();
        }
        return new LocalCacheStorage();
    }
}
