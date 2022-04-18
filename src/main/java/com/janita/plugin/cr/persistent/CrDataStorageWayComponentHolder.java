package com.janita.plugin.cr.persistent;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.util.ui.JBUI;
import com.janita.plugin.cr.domain.CrDataStorageWay;
import com.janita.plugin.cr.util.CrConstants;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    protected JButton clearButton;

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
        enableField(holder, (String) holder.getWayComboBox().getSelectedItem());
        // 添加下拉事件
        holder.wayComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String item = (String) e.getItem();
                enableField(holder, item);
            }
        });

        holder.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                holder.getWayComboBox().setSelectedItem(CrConstants.LOCAL_CACHE);
                holder.getUrlField().setText("");
                holder.getPwdField().setText("");
                holder.getRestDomainField().setText("");
            }
        });
        return holder;
    }

    private static void enableField(CrDataStorageWayComponentHolder holder, String way) {
        if (CrConstants.REST_WAY.equals(way)) {
            holder.urlField.setEnabled(false);
            holder.pwdField.setEnabled(false);
            holder.restDomainField.setEnabled(true);
        } else if (CrConstants.DB_WAY.equals(way)) {
            holder.urlField.setEnabled(true);
            holder.pwdField.setEnabled(true);
            holder.restDomainField.setEnabled(false);
        } else if (CrConstants.LOCAL_CACHE.equals(way)) {
            holder.urlField.setEnabled(false);
            holder.pwdField.setEnabled(false);
            holder.restDomainField.setEnabled(false);
        }
    }

    private static CrDataStorageWayComponentHolder createPanelBox(CrDataStorageWay storageWay) {
        Box totalBox = Box.createVerticalBox();
        // 创建水平框
        Box wayBox = Box.createHorizontalBox();
        wayBox.setPreferredSize(JBUI.size(150, 40));
        wayBox.add(new JLabel("存储的方式:"));
        wayBox.add(Box.createHorizontalStrut(15));
        JComboBox<String> wayComboBox = new ComboBox<>(new String[] { CrConstants.LOCAL_CACHE, CrConstants.REST_WAY, CrConstants.DB_WAY });
        wayComboBox.setPreferredSize(JBUI.size(50, 20));
        if (storageWay.getStorageWay() != null && storageWay.getStorageWay().trim().length() != 0) {
            wayComboBox.setSelectedItem(storageWay.getStorageWay());
        }
        wayBox.add(wayComboBox);

        JButton clearButton = new JButton("清除");
        wayBox.add(clearButton);
        totalBox.add(wayBox);

        Box urlBox = Box.createHorizontalBox();
        urlBox.setPreferredSize(JBUI.size(150, 40));
        urlBox.add(new JLabel("数据库地址："));
        urlBox.add(Box.createHorizontalStrut(10));
        JTextField urlField = new JTextField(storageWay.getDataUrl());
        urlField.setPreferredSize(JBUI.size(150, 40));
        urlBox.add(urlField);
        totalBox.add(urlBox);

        Box pwdBox = Box.createHorizontalBox();
        pwdBox.setPreferredSize(JBUI.size(150, 40));
        pwdBox.add(new JLabel("数据库密码："));
        pwdBox.add(Box.createHorizontalStrut(10));
        JTextField pwdField = new JTextField(storageWay.getDataPwd());
        pwdField.setPreferredSize(JBUI.size(150, 40));
        pwdBox.add(pwdField);
        totalBox.add(pwdBox);

        Box domainBox = Box.createHorizontalBox();
        domainBox.setPreferredSize(JBUI.size(150, 40));
        domainBox.add(new JLabel("数据的域名："));
        domainBox.add(Box.createHorizontalStrut(10));
        JTextField restDomainField = new JTextField(storageWay.getRestDomain());
        restDomainField.setPreferredSize(JBUI.size(150, 40));
        domainBox.add(restDomainField);
        totalBox.add(domainBox);

        totalBox.setPreferredSize(JBUI.size(100, 100));

        return CrDataStorageWayComponentHolder.builder()
                .totalPanel(totalBox)
                .clearButton(clearButton)
                .wayComboBox(wayComboBox)
                .urlField(urlField)
                .pwdField(pwdField)
                .restDomainField(restDomainField)
                .build();
    }
}
