package com.janita.plugin.cr.persistent;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.ui.JBUI;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.cr.domain.CrDataStorage;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

/**
 * CrDataStorageWayComponentHolder
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@Builder
public class CrDataStorageDialogComponentHolder {

    private JComponent totalPanel;

    protected JComboBox<CrDataStorageEnum> wayComboBox;

    protected JTextField urlField;

    protected JTextField pwdField;

    protected JTextField restDomainField;

    protected JButton clearButton;

    public CrDataStorage getCrDataStorageWay() {
        CrDataStorageEnum name = (CrDataStorageEnum) wayComboBox.getSelectedItem();
        String url = urlField.getText();
        String pwd = pwdField.getText();
        String domain = restDomainField.getText();
        PropertiesComponent.getInstance().setValue(PersistentKeys.MYSQL_URL, url);
        PropertiesComponent.getInstance().setValue(PersistentKeys.MYSQL_USERNAME, null);
        PropertiesComponent.getInstance().setValue(PersistentKeys.MYSQL_PWD, pwd);
        return new CrDataStorage(name, url, pwd, domain);
    }

    public static CrDataStorageDialogComponentHolder createCrDataStorageComponentHolder(boolean setting, CrDataStorage storageWay) {
        storageWay = ObjectUtils.defaultIfNull(storageWay, new CrDataStorage());
        CrDataStorageDialogComponentHolder holder = createPanelBox(setting, storageWay);
        // 下拉默认是 rest接口，所以数据库不可用
        enableField(holder, (CrDataStorageEnum) holder.getWayComboBox().getSelectedItem());
        // 添加下拉事件
        holder.wayComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                CrDataStorageEnum item = (CrDataStorageEnum) e.getItem();
                enableField(holder, item);
            }
        });

        JButton clearButton = holder.clearButton;
        if (clearButton == null) {
            return holder;
        }

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrDataStoragePersistent.getInstance().loadState(new CrDataStorage());

            }
        });
        return holder;
    }

    private static void enableField(CrDataStorageDialogComponentHolder holder, CrDataStorageEnum way) {
        if (CrDataStorageEnum.REST_API == way) {
            holder.urlField.setEnabled(false);
            holder.pwdField.setEnabled(false);
            holder.restDomainField.setEnabled(true);
        } else if (CrDataStorageEnum.MYSQL_DB == way) {
            holder.urlField.setEnabled(true);
            holder.pwdField.setEnabled(true);
            holder.restDomainField.setEnabled(false);
        } else if (CrDataStorageEnum.LOCAL_CACHE == way) {
            holder.urlField.setEnabled(false);
            holder.pwdField.setEnabled(false);
            holder.restDomainField.setEnabled(false);
        }
    }

    private static CrDataStorageDialogComponentHolder createPanelBox(boolean setting, CrDataStorage storageWay) {
        Box totalBox = Box.createVerticalBox();
        // 创建水平框
        Box wayBox = Box.createHorizontalBox();
        wayBox.setPreferredSize(JBUI.size(150, 40));
        wayBox.add(new JLabel("存储的方式:"), BorderLayout.WEST);
        wayBox.add(Box.createHorizontalStrut(15));
        Set<CrDataStorageEnum> supportSet = CrDataStorageEnum.getSupportSet();
        JComboBox<CrDataStorageEnum> wayComboBox = new ComboBox<>(supportSet.toArray(new CrDataStorageEnum[0]));
        wayComboBox.setPreferredSize(JBUI.size(50, 20));
        if (storageWay.getStorageWay() != null) {
            wayComboBox.setSelectedItem(storageWay.getStorageWay());
        }
        wayBox.add(wayComboBox, BorderLayout.EAST);

        // 设置界面来的
        JButton clearButton = null;
        if (setting) {
            clearButton = new JButton("清除");
            wayBox.add(clearButton);
        }
        totalBox.add(wayBox);

        Box urlBox = Box.createHorizontalBox();
        urlBox.setPreferredSize(JBUI.size(150, 40));
        urlBox.add(new JLabel("数据库地址："), BorderLayout.WEST);
        urlBox.add(Box.createHorizontalStrut(10));
        JTextField urlField = new JTextField(storageWay.getDbUrl());
        urlField.setPreferredSize(JBUI.size(150, 40));
        urlBox.add(urlField, BorderLayout.EAST);
        totalBox.add(urlBox);

        Box pwdBox = Box.createHorizontalBox();
        pwdBox.setPreferredSize(JBUI.size(150, 40));
        pwdBox.add(new JLabel("数据库密码："), BorderLayout.WEST);
        pwdBox.add(Box.createHorizontalStrut(10));
        JTextField pwdField = new JTextField(storageWay.getDbPwd());
        pwdField.setPreferredSize(JBUI.size(150, 40));
        pwdBox.add(pwdField, BorderLayout.EAST);
        totalBox.add(pwdBox);

        Box domainBox = Box.createHorizontalBox();
        domainBox.setPreferredSize(JBUI.size(150, 40));
        domainBox.add(new JLabel("数据的域名："), BorderLayout.WEST);
        domainBox.add(Box.createHorizontalStrut(10));
        JTextField restDomainField = new JTextField(storageWay.getRestApiDomain());
        restDomainField.setPreferredSize(JBUI.size(150, 40));
        domainBox.add(restDomainField, BorderLayout.EAST);
        totalBox.add(domainBox);

        totalBox.setPreferredSize(JBUI.size(100, 100));

        return CrDataStorageDialogComponentHolder.builder()
                .totalPanel(totalBox)
                .clearButton(clearButton)
                .wayComboBox(wayComboBox)
                .urlField(urlField)
                .pwdField(pwdField)
                .restDomainField(restDomainField)
                .build();
    }
}
