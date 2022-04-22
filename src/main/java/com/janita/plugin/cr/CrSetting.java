package com.janita.plugin.cr;

import com.intellij.openapi.ui.DialogWrapper;
import com.janita.plugin.demo.layout.SettingDemo;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * CrSetting
 *
 * @author zhucj
 * @since 20220324
 */
public class CrSetting extends DialogWrapper {

    protected CrSetting() {
        super(true);
        init();
    }

    @Override
    protected @Nullable
    JComponent createCenterPanel() {
        return new SettingDemo().show();
    }
}
