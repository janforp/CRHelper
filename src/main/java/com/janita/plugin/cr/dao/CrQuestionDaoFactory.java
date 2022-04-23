package com.janita.plugin.cr.dao;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.dao.impl.CrQuestionDbDAO;
import com.janita.plugin.cr.dao.impl.CrQuestionIdeaCacheDAO;
import com.janita.plugin.cr.setting.CrQuestionSetting;

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
        CrDataStorageEnum storageWayEnum = CrQuestionSetting.getStorageWayFromCache();
        if (storageWayEnum == CrDataStorageEnum.LOCAL_CACHE) {
            return ideaCacheDAO;
        }
        return sqliteDAO;
    }
}