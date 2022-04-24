package com.janita.plugin.common.util;

import lombok.experimental.UtilityClass;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * JSwingUtils
 *
 * @author zhucj
 * @since 20220324
 */
@UtilityClass
public class JSwingUtils {

    public void showErrorDialog(String title, String errorMsg) {
        JOptionPane.showMessageDialog(null, errorMsg, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * 列表内容局中
     *
     * @param table 列表
     */
    public void setTableTextCenter(JTable table) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
    }
}
