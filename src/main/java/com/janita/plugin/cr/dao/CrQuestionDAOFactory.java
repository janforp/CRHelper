package com.janita.plugin.cr.dao;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;

/**
 * CrQuestionDAOFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionDAOFactory {

    private static final CrQuestionDAOSqlite crQuestionDAO = new CrQuestionDAOSqlite();

    public static CrQuestionDAOSqlite getDAO() {
        CrDataStorageEnum storageWay = CrDataStoragePersistent.getInstance().getState().getStorageWay();
        if (storageWay == CrDataStorageEnum.LOCAL_CACHE) {
            return crQuestionDAO;
        }
        return crQuestionDAO;
    }
}
