package com.janita.plugin.cr.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.janita.plugin.cr.domain.CrDataStorageWay;
import com.janita.plugin.cr.persistent.CrDataStorageWayComponentHolder;
import com.janita.plugin.cr.persistent.CrDataStorageWayPersistent;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CrFetchDataWayDialog
 *
 * @author zhucj
 * @since 20220324
 */
public class CrFetchDataWayDialog extends DialogWrapper {

    private final CrDataStorageWay storageWay;

    private JComboBox<String> wayComboBox;

    private JTextField urlField;

    private JTextField pwdField;

    private JTextField restDomainField;

    public CrFetchDataWayDialog(CrDataStorageWay storageWay) {
        super(true);
        storageWay = ObjectUtils.defaultIfNull(storageWay, new CrDataStorageWay());
        this.storageWay = storageWay;
        init();
        setTitle("数据存储方式");
        setSize(500, 300);
    }

    @Override
    protected @Nullable
    JComponent createCenterPanel() {
        CrDataStorageWayComponentHolder panel = CrDataStorageWayComponentHolder.createCrDataStorageWayPanel(storageWay);
        this.wayComboBox = panel.getWayComboBox();
        this.urlField = panel.getUrlField();
        this.pwdField = panel.getPwdField();
        this.restDomainField = panel.getRestDomainField();
        return panel.getTotalPanel();
    }

    @Override
    protected @Nullable
    ValidationInfo doValidate() {
        String name = (String) wayComboBox.getSelectedItem();
        String url = urlField.getText();
        String pwd = pwdField.getText();
        String domain = restDomainField.getText();
        if ("rest接口".equals(name)) {
            if (domain == null || domain.trim().length() == 0 || !domain.startsWith("http")) {
                return new ValidationInfo("请填写正确的rest接口域名");
            }
        } else {
            if (url == null || url.trim().length() == 0) {
                return new ValidationInfo("请输入数据库地址");
            }
            if (pwd == null || pwd.trim().length() == 0) {
                return new ValidationInfo("请输数据库密码");
            }
        }

        return null;
    }

    public CrDataStorageWay getCrDataStorageWay() {
        String name = (String) wayComboBox.getSelectedItem();
        String url = urlField.getText();
        String pwd = pwdField.getText();
        String domain = restDomainField.getText();
        return new CrDataStorageWay(name, url, pwd, domain);
    }

    public static boolean doBeforeCrAndReturnIfClickOk() {
        CrDataStorageWayPersistent persistent = CrDataStorageWayPersistent.getInstance();
        CrDataStorageWay storageWay = persistent.getState();
        if (storageWay != null) {
            return true;
        }
        CrFetchDataWayDialog dialog = new CrFetchDataWayDialog(null);
        if (dialog.showAndGet()) {
            storageWay = dialog.getCrDataStorageWay();
            persistent.loadState(storageWay);
            return true;
        }
        return false;
    }
}
