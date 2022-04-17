package com.janita.plugin.cr.persistent;

import com.intellij.openapi.ui.ComboBox;
import com.janita.plugin.cr.domain.CrDataStorageWay;
import com.janita.plugin.cr.util.CrConstants;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * CrDataStorageWayComponentHolder
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@Builder
public class CrDataStorageWayComponentHolder {

    private JComponent totalPanel;

    protected JComboBox<String> wayComboBox;

    protected JTextField urlField;

    protected JTextField pwdField;

    protected JTextField restDomainField;

    public CrDataStorageWay getCrDataStorageWay() {
        String name = (String) wayComboBox.getSelectedItem();
        String url = urlField.getText();
        String pwd = pwdField.getText();
        String domain = restDomainField.getText();
        return new CrDataStorageWay(name, url, pwd, domain);
    }

    public static CrDataStorageWayComponentHolder createCrDataStorageWayPanel(CrDataStorageWay storageWay) {
        storageWay = ObjectUtils.defaultIfNull(storageWay, new CrDataStorageWay());
        CrDataStorageWayComponentHolder holder = createPanelBox(storageWay);
        // 下拉默认是 rest接口，所以数据库不可用
        holder.urlField.setEnabled(false);
        holder.pwdField.setEnabled(false);
        holder.restDomainField.setEnabled(false);

        // 添加下拉事件
        holder.wayComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = (String) e.getItem();
                if ("REST接口".equals(item)) {
                    holder.urlField.setEnabled(false);
                    holder.pwdField.setEnabled(false);
                    holder.restDomainField.setEnabled(true);
                } else if ("数据库".equals(item)) {
                    holder.urlField.setEnabled(true);
                    holder.pwdField.setEnabled(true);
                    holder.restDomainField.setEnabled(false);
                } else {
                    holder.urlField.setEnabled(false);
                    holder.pwdField.setEnabled(false);
                    holder.restDomainField.setEnabled(false);
                }
            }
        });
        return holder;
    }

    private static CrDataStorageWayComponentHolder createPanelBox(CrDataStorageWay storageWay) {
        Box totalBox = Box.createVerticalBox();
        // 创建水平框
        Box wayBox = Box.createHorizontalBox();
        wayBox.setPreferredSize(new Dimension(100, 20));
        wayBox.add(new JLabel("存储的方式:"));
        wayBox.add(Box.createHorizontalStrut(15));
        JComboBox<String> wayComboBox = new ComboBox<>(new String[] { CrConstants.LOCAL_CACHE, CrConstants.REST_WAY, CrConstants.DB_WAY });
        wayComboBox.setPreferredSize(new Dimension(50, 20));
        if (storageWay.getWayName() != null && storageWay.getWayName().trim().length() != 0) {
            wayComboBox.setSelectedItem(storageWay.getWayName());
        }
        wayBox.add(wayComboBox);
        totalBox.add(wayBox);

        Box urlBox = Box.createHorizontalBox();
        urlBox.setPreferredSize(new Dimension(100, 20));
        urlBox.add(new JLabel("数据库地址："));
        urlBox.add(Box.createHorizontalStrut(10));
        JTextField urlField = new JTextField(storageWay.getDataUrl());
        urlBox.add(urlField);
        totalBox.add(urlBox);

        Box pwdBox = Box.createHorizontalBox();
        pwdBox.setPreferredSize(new Dimension(100, 20));
        pwdBox.add(new JLabel("数据库密码："));
        pwdBox.add(Box.createHorizontalStrut(10));
        JTextField pwdField = new JTextField(storageWay.getDataPwd());
        pwdBox.add(pwdField);
        totalBox.add(pwdBox);

        Box domainBox = Box.createHorizontalBox();
        domainBox.setPreferredSize(new Dimension(100, 20));
        domainBox.add(new JLabel("数据的域名："));
        domainBox.add(Box.createHorizontalStrut(10));
        JTextField restDomainField = new JTextField(storageWay.getRestDomain());
        domainBox.add(restDomainField);
        totalBox.add(domainBox);

        return CrDataStorageWayComponentHolder.builder()
                .totalPanel(totalBox)
                .wayComboBox(wayComboBox)
                .urlField(urlField)
                .pwdField(pwdField)
                .restDomainField(restDomainField)
                .build();
    }
}
