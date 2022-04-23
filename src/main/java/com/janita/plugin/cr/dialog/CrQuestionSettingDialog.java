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

    public CrQuestionSettingDialog() {
        super(true);
        init();
    }

    @Override
    @Nullable
    protected JComponent createCenterPanel() {
        this.component = CrSettingBuilder.createSettingComponent(false);
        return component.getTotalContent();
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
        if (component.getLocalCacheButton().isSelected() || component.getLocalSqliteDbButton().isSelected()) {
            return null;
        }
        if (component.getRemoteMysqlDbButton().isSelected()) {
            String text = component.getTestField().getText();
            if ("连接失败".equals(text) || "连接测试结果".equals(text)) {
                return new ValidationInfo("请确保数据库连接成功！如果数据库不可用建议使用其他存储方式");
            }
            return null;
        }
        if (component.getRemoteHttpApiButton().isSelected()) {
            // TODO 肯定要检查服务是否监控存活
            return CrDataStorageEnum.REST_API.check(component.getApiDomainField());
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