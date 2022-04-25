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
    public void setTableType(JTable table) {
        // 设置表格行宽
        table.setRowHeight(30);
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

            private final JLabel stateLabel = new JLabel();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (isSelected) {
                    // 如果不是第五列表，则使用默认的
                    return super.getTableCellRendererComponent(table, value, true, hasFocus, row, column);
                }
                boolean stringType = value instanceof String;
                if (!stringType) {
                    return super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
                }
                String text = (String) value;
                CrQuestionState state = CrQuestionState.getByDescOrReturnNull(text);
                if (state == null) {
                    return super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
                }
                stateLabel.setText(text);
                stateLabel.setHorizontalAlignment(CENTER);
                if (CrQuestionState.UNSOLVED == state) {
                    stateLabel.setForeground(JBColor.RED);
                }
                if (CrQuestionState.SOLVED == state) {
                    stateLabel.setForeground(JBColor.GREEN);
                }
                if (CrQuestionState.REJECT == state) {
                    stateLabel.setForeground(JBColor.BLUE);
                }
                if (CrQuestionState.DUPLICATE == state) {
                    stateLabel.setForeground(JBColor.GRAY);
                }
                if (CrQuestionState.CLOSED == state) {
                    stateLabel.setForeground(JBColor.GRAY);
                }
                return stateLabel;
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