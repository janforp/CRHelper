package com.janita.plugin.cr.setting;

import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.common.util.SingletonBeanFactory;
import com.janita.plugin.db.IDatabaseService;
import com.janita.plugin.db.impl.AbstractIDatabaseService;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.Set;

/**
 * CrQuestionSetting
 *
 * @author zhucj
 * @since 20220324
 */
@Data
public class CrQuestionSetting {

    private CrDataStorageEnum storageWay;

    private String dbUrl;

    private String dbUsername;

    private String dbPwd;

    private String restApiDomain;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrQuestionSetting that = (CrQuestionSetting) o;
        return storageWay == that.storageWay
                && Objects.equals(dbUrl, that.dbUrl)
                && Objects.equals(dbUsername, that.dbUsername)
                && Objects.equals(dbPwd, that.dbPwd)
                && Objects.equals(restApiDomain, that.restApiDomain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storageWay, dbUrl, dbUsername, dbPwd, restApiDomain);
    }

    public static boolean saveFromInput(CrQuestionDataStorageSettingComponent component) {
        CrQuestionSetting setting = getCrQuestionSettingFromInput(component);
        SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.CR_DATA_STORAGE_WAY, setting.getStorageWay().getDesc());
        SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.MYSQL_URL, setting.getDbUrl());
        SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.MYSQL_USERNAME, setting.getDbUsername());
        SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.MYSQL_PWD, setting.getDbPwd());
        SingletonBeanFactory.getPropertiesComponent().setValue(PersistentKeys.REST_API_DOMAIN, setting.getRestApiDomain());
        if (setting.getStorageWay() == CrDataStorageEnum.MYSQL_DB || setting.getStorageWay() == CrDataStorageEnum.SQLITE_DB) {
            IDatabaseService database = SingletonBeanFactory.getDatabaseService();
            database.reInitConnect();
            return database.connectSuccess();
        }
        return true;
    }

    public static CrQuestionSetting getCrQuestionSettingFromCache() {
        String way = SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.CR_DATA_STORAGE_WAY);
        CrDataStorageEnum storageEnum = CrDataStorageEnum.getByDesc(way);

        CrQuestionSetting setting = new CrQuestionSetting();
        setting.setStorageWay(storageEnum);
        setting.setDbUrl(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.MYSQL_URL));
        setting.setDbUsername(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.MYSQL_USERNAME));
        setting.setDbPwd(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.MYSQL_PWD));
        setting.setRestApiDomain(SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.REST_API_DOMAIN));
        return setting;
    }

    public static CrDataStorageEnum getStorageWayFromCache() {
        CrQuestionSetting setting = getCrQuestionSettingFromCache();
        return setting.getStorageWay();
    }

    public static CrQuestionSetting getCrQuestionSettingFromInput(CrQuestionDataStorageSettingComponent component) {
        CrDataStorageEnum storageWay = getSelectedStorageWay(component);
        CrQuestionSetting setting = new CrQuestionSetting();
        setting.setStorageWay(storageWay);
        setting.setDbUrl(component.getDbUrlField().getText());
        setting.setDbUsername(component.getDbUsernameField().getText());
        setting.setDbPwd(new String(component.getDbPwdField().getPassword()));
        setting.setRestApiDomain(component.getApiDomainField().getText());
        return setting;
    }

    public static CrDataStorageEnum getSelectedStorageWay(CrQuestionDataStorageSettingComponent component) {
        if (component.getLocalCacheButton().isSelected()) {
            return CrDataStorageEnum.LOCAL_CACHE;
        }
        if (component.getLocalSqliteDbButton().isSelected()) {
            return CrDataStorageEnum.SQLITE_DB;
        }
        if (component.getRemoteMysqlDbButton().isSelected()) {
            return CrDataStorageEnum.MYSQL_DB;
        }
        if (component.getRemoteHttpApiButton().isSelected()) {
            return CrDataStorageEnum.REST_API;
        }
        return CrDataStorageEnum.SQLITE_DB;
    }

    public static boolean checkValid() {
        String storageWay = SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.CR_DATA_STORAGE_WAY);
        CrDataStorageEnum storageEnum = CrDataStorageEnum.getByDesc(storageWay);
        if (storageEnum == null) {
            return false;
        }
        Set<CrDataStorageEnum> supportSet = CrDataStorageEnum.getSupportSet();
        if (!supportSet.contains(storageEnum)) {
            // 如果之前设置的方式已经不支持了，则再次弹出
            return false;
        }
        if (CrDataStorageEnum.LOCAL_CACHE == storageEnum) {
            return true;
        }
        if (CrDataStorageEnum.SQLITE_DB == storageEnum || CrDataStorageEnum.MYSQL_DB == storageEnum) {
            IDatabaseService database = SingletonBeanFactory.getDatabaseService();
            return database.getConnectDirectly() != AbstractIDatabaseService.INVALID_CONNECT;
        }
        if (CrDataStorageEnum.REST_API == storageEnum) {
            String domain = SingletonBeanFactory.getPropertiesComponent().getValue(PersistentKeys.REST_API_DOMAIN);
            return StringUtils.isNotBlank(domain);
        }
        return false;
    }
}