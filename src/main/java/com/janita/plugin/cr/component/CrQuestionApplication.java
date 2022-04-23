package com.janita.plugin.cr.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.dialog.CrQuestionSettingDialog;
import com.janita.plugin.cr.setting.CrQuestionSetting;
import com.janita.plugin.db.IDatabaseService;
import com.janita.plugin.db.impl.AbstractIDatabaseService;
import com.janita.plugin.db.impl.MySqlDatabaseServiceImpl;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;

import java.sql.Connection;

/**
 * CrQuestionApplication
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class CrQuestionApplication implements ApplicationComponent {

    @Override
    public void initComponent() {
        CrQuestionSetting settingFromCache = CrQuestionSetting.getCrQuestionSettingFromCache();
        CrDataStorageEnum storageWay = settingFromCache.getStorageWay();
        if (storageWay == CrDataStorageEnum.MYSQL_DB) {
            IDatabaseService service = MySqlDatabaseServiceImpl.getInstance();
            Connection connection = service.getConnection();
            if (connection == AbstractIDatabaseService.INVALID_CONNECT) {
                CrQuestionSettingDialog dialog = new CrQuestionSettingDialog();
                if (dialog.showAndGet()) {
                    dialog.doOKAction();
                }
            }
        }
    }

    @Override
    public void disposeComponent() {
        SqliteDatabaseServiceImpl.getInstance().closeResource();
        ApplicationComponent.super.disposeComponent();
    }
}
