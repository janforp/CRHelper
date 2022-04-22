package com.janita.plugin.demo.layout;

import javax.swing.*;
import java.awt.*;

/**
 * SettingDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class SettingDemo {

    public static void main(String args[]) {
        SettingDemo ui = new SettingDemo();
        JComponent show = ui.show();
        JFrame jf = new JFrame();
        jf.setSize(450, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);
        jf.setResizable(false);
        jf.add(show);
        jf.setVisible(true);
    }

    public JComponent show() {
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
        jf.setVisible(true);
        return jf;
    }

}