package com.janita.plugin.common.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.dao.ICrQuestionDAO;
import com.janita.plugin.cr.dao.impl.CrQuestionDbDAO;
import com.janita.plugin.cr.dao.impl.CrQuestionIdeaCacheDAO;
import com.janita.plugin.cr.dao.impl.CrQuestionRestApiDAO;
import com.janita.plugin.cr.persistent.CrQuestionDataPersistent;
import com.janita.plugin.cr.service.CrQuestionService;
import com.janita.plugin.cr.setting.CrQuestionSetting;
import com.janita.plugin.db.IDatabaseService;
import com.janita.plugin.db.impl.MySqlDatabaseServiceImpl;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单例
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class SingletonBeanFactory {

    private static final Map<String, Set<Object>> BEAN_MAP = new ConcurrentHashMap<>();

    public IDatabaseService getMySqlDatabaseServiceImpl() {
        MySqlDatabaseServiceImpl service = ApplicationManager.getApplication().getService(MySqlDatabaseServiceImpl.class);
        doAfterGetBean("MySqlDatabaseServiceImpl", service);
        return service;
    }

    public static IDatabaseService getSqliteDatabaseServiceImpl() {
        SqliteDatabaseServiceImpl service = ApplicationManager.getApplication().getService(SqliteDatabaseServiceImpl.class);
        doAfterGetBean("SqliteDatabaseServiceImpl", service);
        return service;
    }

    public static IDatabaseService getDatabaseService() {
        CrDataStorageEnum storageWay = CrQuestionSetting.getStorageWayFromCache();
        if (storageWay == CrDataStorageEnum.MYSQL_DB) {
            return SingletonBeanFactory.getMySqlDatabaseServiceImpl();
        }
        return SingletonBeanFactory.getSqliteDatabaseServiceImpl();
    }

    public static CrQuestionService getCrQuestionService() {
        CrQuestionService service = ApplicationManager.getApplication().getService(CrQuestionService.class);
        doAfterGetBean("CrQuestionService", service);
        return service;
    }

    public static CrQuestionDataPersistent getCrQuestionDataPersistent() {
        CrQuestionDataPersistent service = ServiceManager.getService(CrQuestionDataPersistent.class);
        doAfterGetBean("CrQuestionDataPersistent", service);
        return service;
    }

    public static PropertiesComponent getPropertiesComponent() {
        PropertiesComponent component = PropertiesComponent.getInstance();
        doAfterGetBean("PropertiesComponent", component);
        return component;
    }

    public static CrQuestionDbDAO getCrQuestionDbDAO() {
        CrQuestionDbDAO service = ApplicationManager.getApplication().getService(CrQuestionDbDAO.class);
        doAfterGetBean("CrQuestionDbDAO", service);
        return service;
    }

    public static CrQuestionIdeaCacheDAO getCrQuestionIdeaCacheDAO() {
        CrQuestionIdeaCacheDAO service = ApplicationManager.getApplication().getService(CrQuestionIdeaCacheDAO.class);
        doAfterGetBean("CrQuestionIdeaCacheDAO", service);
        return service;
    }

    public static CrQuestionRestApiDAO getCrQuestionRestApiDAO() {
        CrQuestionRestApiDAO service = ApplicationManager.getApplication().getService(CrQuestionRestApiDAO.class);
        doAfterGetBean("CrQuestionRestApiDAO", service);
        return service;
    }

    public static ICrQuestionDAO getCrQuestionDAO() {
        CrDataStorageEnum storageWayEnum = CrQuestionSetting.getStorageWayFromCache();
        if (storageWayEnum == CrDataStorageEnum.LOCAL_CACHE) {
            return SingletonBeanFactory.getCrQuestionIdeaCacheDAO();
        }
        if (storageWayEnum == CrDataStorageEnum.REST_API) {
            return SingletonBeanFactory.getCrQuestionRestApiDAO();
        }
        return SingletonBeanFactory.getCrQuestionDbDAO();
    }

    @SuppressWarnings("all")
    private static void doAfterGetBean(String beanName, Object bean) {
        Set<Object> objectSet = BEAN_MAP.get(beanName);
        if (objectSet == null) {
            objectSet = new HashSet<>();
            BEAN_MAP.put(beanName, objectSet);
        }
        objectSet.add(bean);
    }
}
