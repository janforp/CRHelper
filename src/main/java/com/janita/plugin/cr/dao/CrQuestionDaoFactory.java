package com.janita.plugin.cr.dao;

import com.intellij.ide.util.PropertiesComponent;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.dao.impl.CrQuestionDbDAO;
import com.janita.plugin.cr.dao.impl.CrQuestionIdeaCacheDAO;

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
        String storageWay = PropertiesComponent.getInstance().getValue(PersistentKeys.CR_DATA_STORAGE_WAY);
        CrDataStorageEnum storageWayEnum = CrDataStorageEnum.getByDesc(storageWay);
        if (storageWayEnum == CrDataStorageEnum.LOCAL_CACHE) {
            return ideaCacheDAO;
        }
        return sqliteDAO;
    }
}