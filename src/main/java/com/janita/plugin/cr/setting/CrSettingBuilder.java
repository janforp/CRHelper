package com.janita.plugin.cr.setting;

import com.intellij.ide.util.PropertiesComponent;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrDataStorageEnum;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * SettingDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class CrSettingBuilder {

    public static CrQuestionDataStorageSettingComponent createSettingComponent(boolean createFromSetting) {
        JPanel panel = new JPanel();
        panel.setSize(450, 300);

        JRadioButton localCacheButton = new JRadioButton("本地缓存");
        localCacheButton.setEnabled(CrDataStorageEnum.LOCAL_CACHE.isSupport());
        localCacheButton.setToolTipText("使用idea本地缓存，清空idea缓存数据将会丢失");

        JRadioButton localSqliteDbButton = new JRadioButton("本地DB");
        localSqliteDbButton.setEnabled(CrDataStorageEnum.SQLITE_DB.isSupport());
        localSqliteDbButton.setToolTipText("使用本地Sqlite数据库，数据持久化在本地磁盘中,路径为：${USER_HOME_PATH}/.ideaCRHelperFile/CRHelper.db");

        JRadioButton remoteMysqlDbButton = new JRadioButton("远程DB");
        remoteMysqlDbButton.setEnabled(CrDataStorageEnum.MYSQL_DB.isSupport());
        remoteMysqlDbButton.setToolTipText("用户自己提供mysql数据库，数据持久化在用户的数据库中");

        JRadioButton remoteHttpApiButton = new JRadioButton("远程接口");
        remoteHttpApiButton.setEnabled(CrDataStorageEnum.REST_API.isSupport());
        remoteHttpApiButton.setToolTipText("用户自己开发的rest服务，通过rest接口进行数据的存储");

        JLabel dbUrlLabel = new JLabel("数据库URL:");
        JLabel dbUsernameLabel = new JLabel("数据库用户:");
        JLabel dbPwdLabel = new JLabel("数据库密码:");
        JLabel apiDomainLabel = new JLabel("接口域名:");

        JTextField dbUrlField = new JTextField(50);
        dbUrlField.setToolTipText("如 jdbc:mysql://ip:port/database?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&rewriteBatchedStatements=true,后面需要添加database名称");
        JTextField dbUsernameField = new JTextField(50);
        JPasswordField dbPwdField = new JPasswordField(50);
        JTextField apiDomainField = new JTextField(50);
        apiDomainField.setToolTipText("如 https://www.baidu.com");

        JButton testDbBtn = new JButton("Test Connect");
        JTextField testField = new JTextField("连接测试结果");
        testField.setEnabled(false);

        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.BOTH;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(localCacheButton, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(localSqliteDbButton, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(remoteMysqlDbButton, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(remoteHttpApiButton, gridBagConstraints);

        Component verticalStrut = Box.createVerticalStrut(30);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(verticalStrut, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(dbUrlLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(dbUsernameLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(dbPwdLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(apiDomainLabel, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(testDbBtn, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(dbUrlField, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(dbUsernameField, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(dbPwdField, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(apiDomainField, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(testField, gridBagConstraints);

        ButtonGroup group = new ButtonGroup();
        group.add(localCacheButton);
        group.add(localSqliteDbButton);
        group.add(remoteMysqlDbButton);
        group.add(remoteHttpApiButton);

        panel.add(localCacheButton);
        panel.add(localSqliteDbButton);
        panel.add(remoteMysqlDbButton);
        panel.add(remoteHttpApiButton);
        panel.add(dbUrlLabel);
        panel.add(dbUsernameLabel);
        panel.add(dbPwdLabel);
        panel.add(apiDomainLabel);
        panel.add(dbUrlField);
        panel.add(dbUsernameField);
        panel.add(dbPwdField);
        panel.add(apiDomainField);
        panel.add(verticalStrut);
        panel.add(testDbBtn);
        panel.add(testField);

        CrQuestionDataStorageSettingComponent component = CrQuestionDataStorageSettingComponent.builder().
                totalContent(panel)
                .group(group)
                .localCacheButton(localCacheButton)
                .localSqliteDbButton(localSqliteDbButton)
                .remoteMysqlDbButton(remoteMysqlDbButton)
                .remoteHttpApiButton(remoteHttpApiButton)
                .dbUrlField(dbUrlField)
                .dbUsernameField(dbUsernameField)
                .dbPwdField(dbPwdField)
                .apiDomainField(apiDomainField)
                .createFromSetting(createFromSetting)
                .testDbButton(testDbBtn)
                .testField(testField)
                .build();
        addActionListener(component);
        selectRadix(component);
        initFieldText(component);
        return component;
    }

    private static void initFieldText(CrQuestionDataStorageSettingComponent component) {
        CrQuestionSetting setting = CrQuestionSetting.getCrQuestionSettingFromCache();
        component.getDbUrlField().setText(setting.getDbUrl());
        component.getDbUsernameField().setText(setting.getDbUsername());
        component.getDbPwdField().setText(setting.getDbPwd());
        component.getApiDomainField().setText(setting.getRestApiDomain());
    }

    private static void selectRadix(CrQuestionDataStorageSettingComponent component) {
        String way = PropertiesComponent.getInstance().getValue(PersistentKeys.CR_DATA_STORAGE_WAY);
        CrDataStorageEnum storageEnum = CrDataStorageEnum.getByDesc(way);
        if (storageEnum == CrDataStorageEnum.LOCAL_CACHE) {
            component.getLocalCacheButton().setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.SQLITE_DB) {
            component.getLocalSqliteDbButton().setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.MYSQL_DB) {
            component.getRemoteMysqlDbButton().setSelected(true);
            return;
        }
        if (storageEnum == CrDataStorageEnum.REST_API) {
            component.getRemoteHttpApiButton().setSelected(true);
            return;
        }
        // 默认
        component.getLocalSqliteDbButton().setSelected(true);
    }

    private static void addActionListener(CrQuestionDataStorageSettingComponent component) {
        component.getLocalCacheButton().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (component.getLocalCacheButton().isSelected()) {
                    component.getDbUrlField().setEnabled(false);
                    component.getDbUsernameField().setEnabled(false);
                    component.getDbPwdField().setEnabled(false);
                    component.getApiDomainField().setEnabled(false);
                    component.getTestField().setText("连接成功");
                }
            }
        });
        component.getLocalSqliteDbButton().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (component.getLocalSqliteDbButton().isSelected()) {
                    component.getDbUrlField().setEnabled(false);
                    component.getDbUsernameField().setEnabled(false);
                    component.getDbPwdField().setEnabled(false);
                    component.getApiDomainField().setEnabled(false);
                    component.getTestField().setText("连接成功");
                }
            }
        });
        component.getRemoteMysqlDbButton().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (component.getRemoteMysqlDbButton().isSelected()) {
                    component.getDbUrlField().setEnabled(true);
                    component.getDbUsernameField().setEnabled(true);
                    component.getDbPwdField().setEnabled(true);
                    component.getApiDomainField().setEnabled(false);
                    component.getTestField().setText("连接测试结果");
                }
            }
        });
        component.getRemoteHttpApiButton().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (component.getRemoteHttpApiButton().isSelected()) {
                    component.getDbUrlField().setEnabled(false);
                    component.getDbUsernameField().setEnabled(false);
                    component.getDbPwdField().setEnabled(false);
                    component.getApiDomainField().setEnabled(true);
                    component.getTestField().setText("连接测试结果");
                }
            }
        });
        component.getTestDbButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestionSetting setting = CrQuestionSetting.getCrQuestionSettingFromInput(component);
                CrDataStorageEnum storageWay = setting.getStorageWay();
                Pair<Boolean, String> connectResult = storageWay.checkConnect(component.getDbUrlField(), component.getDbUsernameField(), component.getDbPwdField(), component.getApiDomainField());
                component.getTestField().setText(connectResult.getRight());
            }
        });
    }
}