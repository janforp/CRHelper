package com.janita.plugin.common.util;

import com.intellij.ui.JBColor;
import com.janita.plugin.common.enums.ButtonType;
import com.janita.plugin.common.enums.CrQuestionState;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
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
        // 设置表格行宽
        table.setRowHeight(30);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                // TODO
                if (value instanceof String) {
                    String text = (String) value;
                    CrQuestionState state = CrQuestionState.getByDesc(text);
                    if (CrQuestionState.UNSOLVED == state) {
                        setBackground(JBColor.GREEN);
                        setForeground(JBColor.RED);
                    }
                    if (CrQuestionState.SOLVED == state) {
                        setBackground(JBColor.WHITE);
                        setForeground(JBColor.BLACK);
                    }
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
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