package com.janita.plugin.cr.remote.strategy;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.remote.strategy.impl.DbStorageStrategy;
import com.janita.plugin.cr.remote.strategy.impl.LocalCacheStorageStrategy;
import com.janita.plugin.cr.remote.strategy.impl.RestServiceStorageStrategy;

/**
 * CrQuestionStorageFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionStorageFactory {

    public static CrQuestionStorageStrategy getCrQuestionStorage(CrDataStorageEnum storageWay) {
        if (CrDataStorageEnum.LOCAL_CACHE == storageWay) {
            return new LocalCacheStorageStrategy();
        }
        if (CrDataStorageEnum.DB == storageWay) {
            return new DbStorageStrategy();
        }
        if (CrDataStorageEnum.REST_API == storageWay) {
            return new RestServiceStorageStrategy();
        }
        return new LocalCacheStorageStrategy();
    }
}