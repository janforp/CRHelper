package com.janita.plugin.cr.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.domain.CrDataStorage;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;
import com.janita.plugin.cr.persistent.CrDataStorageWayComponentHolder;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CrFetchDataWayDialog
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionStorageDialog extends DialogWrapper {

    private final CrDataStorage storageWay;

    private JComboBox<CrDataStorageEnum> storageBox;

    private JTextField urlField;

    private JTextField pwdField;

    private JTextField restDomainField;

    public CrQuestionStorageDialog(CrDataStorage storageWay) {
        super(true);
        storageWay = ObjectUtils.defaultIfNull(storageWay, new CrDataStorage());
        this.storageWay = storageWay;
        init();
        setTitle("数据存储方式");
        setSize(1000, 300);
    }

    @Override
    protected @Nullable
    JComponent createCenterPanel() {
        CrDataStorageWayComponentHolder panel = CrDataStorageWayComponentHolder.createCrDataStorageWayPanel(storageWay);
        this.storageBox = panel.getWayComboBox();
        this.urlField = panel.getUrlField();
        this.pwdField = panel.getPwdField();
        this.restDomainField = panel.getRestDomainField();
        return panel.getTotalPanel();
    }

    @Override
    protected @Nullable
    ValidationInfo doValidate() {
        CrDataStorageEnum storageEnum = (CrDataStorageEnum) storageBox.getSelectedItem();
        if (storageEnum == CrDataStorageEnum.REST_API) {
            return CrDataStorageEnum.REST_API.check(restDomainField);
        } else if (storageEnum == CrDataStorageEnum.DB) {
            return CrDataStorageEnum.DB.check(urlField, pwdField);
        }
        return null;
    }

    public CrDataStorage getCrDataStorageWay() {
        CrDataStorageEnum name = (CrDataStorageEnum) storageBox.getSelectedItem();
        String url = urlField.getText();
        String pwd = pwdField.getText();
        String domain = restDomainField.getText();
        return new CrDataStorage(name, url, pwd, domain);
    }

    public static boolean doBeforeCrAndReturnIfClickOk() {
        CrDataStoragePersistent persistent = CrDataStoragePersistent.getInstance();
        CrDataStorage storageWay = persistent.getState();
        boolean valid = CrDataStorage.checkValid(storageWay);
        if (valid) {
            return true;
        }
        CrQuestionStorageDialog dialog = new CrQuestionStorageDialog(null);
        if (dialog.showAndGet()) {
            storageWay = dialog.getCrDataStorageWay();
            persistent.loadState(storageWay);
            return true;
        }
        return false;
    }
}
