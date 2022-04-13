package com.janita.plugin.cr.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.janita.plugin.cr.dialog.CrCreateQuestionDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionHouse;

import javax.swing.*;
import java.awt.*;
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

    /**
     * 框架
     */
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

    /**
     * 项目
     */
    private final Project project;

    public CrQuestionListWindow(Project project, ToolWindow toolWindow) {
        this.project = project;
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
                Point p = e.getPoint();
                int row = questionTable.rowAtPoint(p);
                questionTable.setRowSelectionInterval(row, row);
                int button = e.getButton();
                if (button != MouseEvent.BUTTON3) {
                    return;
                }
                JPopupMenu popupMenu = createPopupWhenClickRightMouse(row);
                popupMenu.show(questionTable, e.getX(), e.getY());
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

    private JPopupMenu createPopupWhenClickRightMouse(int row) {
        // 右键框
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem();
        deleteItem.setText("delete");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestionHouse.delete(row);
            }
        });

        JMenuItem detailItem = new JMenuItem();
        detailItem.setText("detail");
        detailItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestion question = CrQuestionHouse.getCrQuestionList().get(row);
                CrCreateQuestionDialog dialog = new CrCreateQuestionDialog(row, project);
                dialog.open(question);
            }
        });
        popupMenu.add(deleteItem);
        popupMenu.add(detailItem);
        return popupMenu;
    }

    private void onCancel() {
        dispose();
    }
}
