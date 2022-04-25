package com.janita.plugin.cr.renderer;

import com.intellij.ui.JBColor;
import com.janita.plugin.common.enums.CrQuestionState;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * CrQuestionTableRednderer
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionTableRenderer extends DefaultTableCellRenderer {

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
}
