package com.janita.plugin.common.util;

import com.janita.plugin.common.enums.ButtonType;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.MouseEvent;

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

    public ButtonType getMouseButtonType(MouseEvent mouseEvent) {
        int button = mouseEvent.getButton();
        if (button == MouseEvent.BUTTON1) {
            return ButtonType.LEFT;
        }
        if (button == MouseEvent.BUTTON3) {
            return ButtonType.RIGHT;
        }
        return ButtonType.MIDDLE;
    }
}