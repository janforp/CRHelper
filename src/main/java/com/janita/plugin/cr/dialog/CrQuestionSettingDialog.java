package com.janita.plugin.cr.dialog;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.setting.CrQuestionDataStorageSettingComponent;
import com.janita.plugin.cr.setting.SettingBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CrQuestionSettingDialog
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionSettingDialog extends DialogWrapper {

    private JComponent totalContent;

    private JRadioButton localCacheButton;

    private JRadioButton localSqliteDbButton;

    private JRadioButton remoteMysqlDbButton;

    private JRadioButton remoteHttpApiButton;

    private JTextField dbUrlField;

    private JTextField dbUsernameField;

    private JPasswordField dbPwdField;

    private JTextField apiDomainField;

    private final CrDataStorageEnum storageEnum;

    public CrQuestionSettingDialog() {
        super(true);
        this.storageEnum = CrDataStorageEnum.getByDesc(PropertiesComponent.getInstance().getValue(PersistentKeys.CR_DATA_STORAGE_WAY));
        init();
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        CrQuestionDataStorageSettingComponent component = SettingBuilder.createSettingComponent();
        initFields(component);
        selectRadix();
        initFieldText();
        return totalContent;
    }

    private void initFieldText() {
        dbUrlField.setText(PropertiesComponent.getInstance().getValue(PersistentKeys.MYSQL_URL));
        dbUsernameField.setText(PropertiesComponent.getInstance().getValue(PersistentKeys.MYSQL_USERNAME));
        dbPwdField.setText(PropertiesComponent.getInstance().getValue(PersistentKeys.MYSQL_PWD));
        apiDomainField.setText(PropertiesComponent.getInstance().getValue(PersistentKeys.REST_API_DOMAIN));
    }

    private void selectRadix() {
        if (storageEnum == CrDataStorageEnum.LOCAL_CACHE) {
            localCacheButton.setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.SQLITE_DB) {
            localSqliteDbButton.setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.MYSQL_DB) {
            remoteMysqlDbButton.setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.REST_API) {
            remoteHttpApiButton.setSelected(true);
            return;
        }
        // 默认
        localSqliteDbButton.setSelected(true);
    }

    private void initFields(CrQuestionDataStorageSettingComponent component) {
        this.totalContent = component.getTotalContent();
        this.localCacheButton = component.getLocalCacheButton();
        this.localSqliteDbButton = component.getLocalSqliteDbButton();
        this.remoteMysqlDbButton = component.getRemoteMysqlDbButton();
        this.remoteHttpApiButton = component.getRemoteHttpApiButton();
        this.dbUrlField = component.getDbUrlField();
        this.dbUsernameField = component.getDbUsernameField();
        this.dbPwdField = component.getDbPwdField();
        this.apiDomainField = component.getApiDomainField();
    }

    @Override
    public void doOKAction() {
        saveWhenPressOk();
        super.doOKAction();
    }

    private void saveWhenPressOk() {
        ValidationInfo validationInfo = doValidate();
        if (validationInfo == null) {
            PropertiesComponent.getInstance().setValue(PersistentKeys.CR_DATA_STORAGE_WAY, getSelectedStorageWay());
            PropertiesComponent.getInstance().setValue(PersistentKeys.MYSQL_URL, dbUrlField.getText().trim());
            PropertiesComponent.getInstance().setValue(PersistentKeys.MYSQL_USERNAME, dbUsernameField.getText().trim());
            PropertiesComponent.getInstance().setValue(PersistentKeys.MYSQL_PWD, new String(dbPwdField.getPassword()));
            PropertiesComponent.getInstance().setValue(PersistentKeys.REST_API_DOMAIN, apiDomainField.getText().trim());
        }
    }

    private String getSelectedStorageWay() {
        if (localCacheButton.isSelected()) {
            return CrDataStorageEnum.LOCAL_CACHE.getDesc();
        }
        if (localSqliteDbButton.isSelected()) {
            return CrDataStorageEnum.SQLITE_DB.getDesc();
        }
        if (remoteMysqlDbButton.isSelected()) {
            return CrDataStorageEnum.MYSQL_DB.getDesc();
        }
        if (remoteHttpApiButton.isSelected()) {
            return CrDataStorageEnum.REST_API.getDesc();
        }
        return CrDataStorageEnum.SQLITE_DB.getDesc();
    }

    @Override
    @Nullable
    protected ValidationInfo doValidate() {
        if (localCacheButton.isSelected() || localSqliteDbButton.isSelected()) {
            return null;
        }
        if (remoteMysqlDbButton.isSelected()) {
            return CrDataStorageEnum.MYSQL_DB.check(dbUrlField, dbUsernameField, dbPwdField);
        }
        if (remoteHttpApiButton.isSelected()) {
            return CrDataStorageEnum.REST_API.check(apiDomainField);
        }
        return null;
    }
}