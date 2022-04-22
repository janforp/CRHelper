package com.janita.plugin.cr.setting;

import com.intellij.ide.util.PropertiesComponent;
import com.janita.plugin.common.constant.PersistentKeys;
import com.janita.plugin.common.enums.CrDataStorageEnum;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * SettingDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class SettingBuilder {

    public static CrQuestionDataStorageSettingComponent createSettingComponent() {
        JPanel jf = new JPanel();
        jf.setSize(450, 300);
        JRadioButton localCacheButton = new JRadioButton("本地缓存");
        JRadioButton localSqliteDbButton = new JRadioButton("本地DB");
        JRadioButton remoteMysqlDbButton = new JRadioButton("远程DB");
        JRadioButton remoteHttpApiButton = new JRadioButton("远程接口");

        JLabel dbUrlLabel = new JLabel("数据库URL:");
        JLabel dbUsernameLabel = new JLabel("数据库用户:");
        JLabel dbPwdLabel = new JLabel("数据库密码:");
        JLabel apiDomainLabel = new JLabel("接口域名:");

        JTextField dbUrlField = new JTextField();
        JTextField dbUsernameField = new JTextField();
        JPasswordField dbPwdField = new JPasswordField();
        JTextField apiDomainField = new JTextField();

        GridBagLayout gridBagLayout = new GridBagLayout();
        jf.setLayout(gridBagLayout);

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

        ButtonGroup group = new ButtonGroup();
        group.add(localCacheButton);
        group.add(localSqliteDbButton);
        group.add(remoteMysqlDbButton);
        group.add(remoteHttpApiButton);

        jf.add(localCacheButton);
        jf.add(localSqliteDbButton);
        jf.add(remoteMysqlDbButton);
        jf.add(remoteHttpApiButton);
        jf.add(dbUrlLabel);
        jf.add(dbUsernameLabel);
        jf.add(dbPwdLabel);
        jf.add(apiDomainLabel);
        jf.add(dbUrlField);
        jf.add(dbUsernameField);
        jf.add(dbPwdField);
        jf.add(apiDomainField);
        jf.add(verticalStrut);

        CrQuestionDataStorageSettingComponent component = CrQuestionDataStorageSettingComponent.builder().
                totalContent(jf)
                .group(group)
                .localCacheButton(localCacheButton)
                .localSqliteDbButton(localSqliteDbButton)
                .remoteMysqlDbButton(remoteMysqlDbButton)
                .remoteHttpApiButton(remoteHttpApiButton)
                .dbUrlField(dbUrlField)
                .dbUsernameField(dbUsernameField)
                .dbPwdField(dbPwdField)
                .apiDomainField(apiDomainField)
                .build();
        addActionListener(component);
        selectRadix(component);
        initFieldText(component);
        return component;
    }

    private static void initFieldText(CrQuestionDataStorageSettingComponent component) {
        component.getDbUrlField().setText(PropertiesComponent.getInstance().getValue(PersistentKeys.MYSQL_URL));
        component.getDbUsernameField().setText(PropertiesComponent.getInstance().getValue(PersistentKeys.MYSQL_USERNAME));
        component.getDbPwdField().setText(PropertiesComponent.getInstance().getValue(PersistentKeys.MYSQL_PWD));
        component.getApiDomainField().setText(PropertiesComponent.getInstance().getValue(PersistentKeys.REST_API_DOMAIN));
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
                }
            }
        });
    }
}