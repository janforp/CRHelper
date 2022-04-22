package com.janita.plugin.cr.dao;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.dao.impl.CrQuestionDbDAO;
import com.janita.plugin.cr.dao.impl.CrQuestionIdeaCacheDAO;
import com.janita.plugin.cr.domain.CrDataStorage;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;

/**
 * CrQuestionDAOFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionDaoFactory {

    private static final ICrQuestionDAO sqliteDAO = new CrQuestionDbDAO();

    private static final ICrQuestionDAO ideaCacheDAO = new CrQuestionIdeaCacheDAO();

    public static ICrQuestionDAO getDAO() {
        CrDataStoragePersistent persistent = CrDataStoragePersistent.getInstance();
        CrDataStorage storage = persistent.getState();
        if (storage == null) {
            return sqliteDAO;
        }
        CrDataStorageEnum storageWay = storage.getStorageWay();
        if (storageWay == CrDataStorageEnum.LOCAL_CACHE) {
            return ideaCacheDAO;
        }
        return sqliteDAO;
    }
}