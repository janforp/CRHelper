package com.janita.plugin.cr.remote.strategy;

import com.janita.plugin.common.constant.CrConstants;
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

    public static CrQuestionStorage getCrQuestionStorage(String storageWay) {
        if (CrConstants.LOCAL_CACHE.equals(storageWay)) {
            return new LocalCacheStorage();
        }
        if (CrConstants.DB_WAY.equals(storageWay)) {
            return new DbStorage();
        }
        if (CrConstants.REST_WAY.equals(storageWay)) {
            return new RestServiceStorage();
        }
        return new LocalCacheStorage();
    }
}
