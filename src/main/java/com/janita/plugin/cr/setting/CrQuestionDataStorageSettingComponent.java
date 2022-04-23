package com.janita.plugin.cr.setting;

import lombok.Builder;
import lombok.Getter;

import javax.swing.*;

/**
 * CrQuestionDataStorageSettingComponent
 *
 * @author zhucj
 * @since 20220324
 */
@Builder
@Getter
public class CrQuestionDataStorageSettingComponent {

    private final boolean createFromSetting;

    private final JComponent totalContent;

    private final ButtonGroup group;

    private final JRadioButton localCacheButton;

    private final JRadioButton localSqliteDbButton;

    private final JRadioButton remoteMysqlDbButton;

    private final JRadioButton remoteHttpApiButton;

    private final JTextField dbUrlField;

    private final JTextField dbUsernameField;

    private final JPasswordField dbPwdField;

    private final JTextField apiDomainField;

    private final JButton testDbButton;

    private final JTextField testField;
}