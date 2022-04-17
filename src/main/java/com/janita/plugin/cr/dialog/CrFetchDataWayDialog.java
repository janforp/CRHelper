package com.janita.plugin.cr.dialog;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.janita.plugin.cr.domain.CrDataStorageWay;
import com.janita.plugin.cr.persistent.CrDataStorageWayPersistent;
import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.intellij.openapi.ui.ComboBox;

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
        Box panelBox = createPanelBox();

        // 下拉默认是 rest接口，所以数据库不可用
        urlField.setEnabled(false);
        pwdField.setEnabled(false);
        restDomainField.setEnabled(true);

        // 添加下拉事件
        wayComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = (String) e.getItem();
                if ("rest接口".equals(item)) {
                    urlField.setEnabled(false);
                    pwdField.setEnabled(false);
                    restDomainField.setEnabled(true);
                } else {
                    urlField.setEnabled(true);
                    pwdField.setEnabled(true);
                    restDomainField.setEnabled(false);
                }
            }
        });
        return panelBox;
    }

    private Box createPanelBox() {
        // 创建垂直框
        Box totalBox = Box.createVerticalBox();

        // 创建水平框
        Box wayBox = Box.createHorizontalBox();
        wayBox.add(new JLabel("存储的方式:"));
        wayBox.add(Box.createHorizontalStrut/*创建水平支柱*/(15));
        wayComboBox = new ComboBox<>();
        wayComboBox.addItem("rest接口");
        wayComboBox.addItem("数据库");
        if (storageWay.getWayName() != null && storageWay.getWayName().trim().length() != 0) {
            wayComboBox.setSelectedItem(storageWay.getWayName());
        }
        wayBox.add(wayComboBox);
        totalBox.add(wayBox);

        Box urlBox = Box.createHorizontalBox();
        urlBox.add(new JLabel("数据库地址："));
        urlBox.add(Box.createHorizontalStrut(10));
        urlField = new JTextField(storageWay.getDataUrl());
        urlBox.add(urlField);
        totalBox.add(urlBox);

        Box pwdBox = Box.createHorizontalBox();
        pwdBox.add(new JLabel("数据库密码："));
        pwdBox.add(Box.createHorizontalStrut(10));
        pwdField = new JTextField(storageWay.getDataPwd());
        pwdBox.add(pwdField);
        totalBox.add(pwdBox);

        Box domainBox = Box.createHorizontalBox();
        domainBox.add(new JLabel("数据的域名："));
        domainBox.add(Box.createHorizontalStrut(10));
        restDomainField = new JTextField(storageWay.getRestDomain());
        domainBox.add(restDomainField);
        totalBox.add(domainBox);

        return totalBox;
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
        CrDataStorageWayPersistent persistent = ApplicationManager.getApplication().getService(CrDataStorageWayPersistent.class);
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
