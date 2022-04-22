package com.janita.plugin.cr;

import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

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
        JPanel panel = new JPanel(new GridBagLayout());
        //单选框 本地缓存/本地Sqlite/rest接口/mysql数据库
        JPanel top = new JPanel();

        JPanel middle = new JPanel();
        JPanel down = new JPanel();
        //
        return null;
    }
}
