package com.janita.plugin.cr.window;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.janita.plugin.cr.dialog.CrCreateQuestionDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionHouse;
import com.janita.plugin.cr.util.CrQuestionUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
        // 列表内容局中
        tableTextCenter();
        this.project = project;
        initCrQuestionList();

        setContentPane(contentPane);
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
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Point p = mouseEvent.getPoint();
                int row = questionTable.rowAtPoint(p);
                questionTable.setRowSelectionInterval(row, row);
                int button = mouseEvent.getButton();
                if (button == MouseEvent.BUTTON3) {
                    // 右键
                    JPopupMenu popupMenu = createPopupWhenClickRightMouse(row);
                    popupMenu.show(questionTable, mouseEvent.getX(), mouseEvent.getY());
                    return;
                }
                if (mouseEvent.getClickCount() == 2) {
                    // 双击
                    showQuestionDetailDialog(row);
                }

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

    private void tableTextCenter() {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        questionTable.setDefaultRenderer(Object.class, renderer);
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

        // 删除
        JMenuItem deleteItem = new JMenuItem();
        deleteItem.setText("删除");
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestionHouse.delete(row);
            }
        });

        // 编辑
        JMenuItem editItem = new JMenuItem();
        editItem.setText("详情");
        editItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuestionDetailDialog(row);
            }
        });

        // 解决
        JMenuItem solveItem = new JMenuItem();
        solveItem.setText("解决");
        solveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestion question = CrQuestionHouse.getCrQuestionList().get(row);
                CrQuestionUtils.solveQuestion(row, question);
            }
        });

        popupMenu.add(deleteItem);
        popupMenu.add(editItem);
        popupMenu.add(solveItem);
        return popupMenu;
    }

    private void showQuestionDetailDialog(int row) {
        CrQuestion question = CrQuestionHouse.getCrQuestionList().get(row);
        CrCreateQuestionDialog dialog = new CrCreateQuestionDialog(row, project);
        dialog.open(question);
    }

    private void onCancel() {
        dispose();
    }
}
