package com.janita.plugin.cr.window;

import com.intellij.openapi.wm.ToolWindow;
import com.janita.plugin.cr.domain.CrQuestionHouse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * cr问题列表
 *
 * @author zhucj
 * @since 20220428
 */
public class CrQuestionListWindow extends JDialog {

    private JPanel contentPane;

    /**
     * 导出按钮
     */
    private JButton exportButton;

    /**
     * 关闭按钮
     */
    private JButton closeCancel;

    /**
     * 问题列表组件
     */
    private JTable questionTable;

    public CrQuestionListWindow(ToolWindow toolWindow) {

        initCrQuestionList();

        setContentPane(contentPane);
        //        setModal(true);
        getRootPane().setDefaultButton(exportButton);

        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO 导出
                export();
            }
        });

        closeCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 关闭即可
                toolWindow.hide(null);
            }
        });

        questionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void export() {
        // TODO 导出
    }

    private void initCrQuestionList() {
        questionTable.setModel(CrQuestionHouse.TABLE_MODEL);
        questionTable.setEnabled(false);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    //
    //    public static void main(String[] args) {
    //        CrQuestionListWindow dialog = new CrQuestionListWindow();
    //        dialog.pack();
    //        dialog.setVisible(true);
    //        System.exit(0);
    //    }
}
