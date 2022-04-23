package com.janita.plugin.cr.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.setting.CrQuestionDataStorageSettingComponent;
import com.janita.plugin.cr.setting.CrQuestionSetting;
import com.janita.plugin.cr.setting.CrSettingBuilder;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CrQuestionSettingDialog
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionSettingDialog extends DialogWrapper {

    private CrQuestionDataStorageSettingComponent component;

    private JComponent totalContent;

    private JRadioButton localCacheButton;

    private JRadioButton localSqliteDbButton;

    private JRadioButton remoteMysqlDbButton;

    private JRadioButton remoteHttpApiButton;

    private JTextField dbUrlField;

    private JTextField dbUsernameField;

    private JPasswordField dbPwdField;

    private JTextField apiDomainField;

    public CrQuestionSettingDialog() {
        super(true);
        init();
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        CrQuestionDataStorageSettingComponent component = CrSettingBuilder.createSettingComponent(false);
        this.component = component;
        initFields(component);
        return totalContent;
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
            CrQuestionSetting.saveFromInput(component);
        }
    }

    @Override
    @Nullable
    protected ValidationInfo doValidate() {
        // 还要确保输入的配置正确
        if (localCacheButton.isSelected() || localSqliteDbButton.isSelected()) {
            return null;
        }
        if (remoteMysqlDbButton.isSelected()) {
            return CrDataStorageEnum.MYSQL_DB.check(dbUrlField, dbUsernameField, dbPwdField);
        }
        if (remoteHttpApiButton.isSelected()) {
            // TODO 肯定要检查服务是否监控存活
            return CrDataStorageEnum.REST_API.check(apiDomainField);
        }
        return null;
    }

    public static boolean checkStorageAndReturnIfClickOk() {
        boolean valid = CrQuestionSetting.checkValid();
        if (valid) {
            return true;
        }
        CrQuestionSettingDialog dialog = new CrQuestionSettingDialog();
        if (dialog.showAndGet()) {
            dialog.doOKAction();
            return true;
        }
        return false;
    }
}