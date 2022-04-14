package com.janita.plugin.cr.window;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.janita.plugin.cr.dialog.CrCreateQuestionDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionHouse;
import com.janita.plugin.cr.export.MDFreeMarkProcessor;
import com.janita.plugin.cr.util.CrQuestionUtils;
import com.janita.plugin.util.CommonUtils;
import com.janita.plugin.util.DateUtils;

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
import java.io.File;

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
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getBaseDir());
        if (virtualFile == null) {
            return;
        }
        String path = virtualFile.getPath();
        String fullPath = path + File.separator + "CodeReview.md";
        MDFreeMarkProcessor processor = new MDFreeMarkProcessor();
        try {
            processor.process(DateUtils.getCurrentDateTime() + "-CodeReview.md", fullPath, CrQuestionHouse.getCrQuestionList());
            NotificationGroup notificationGroup = new NotificationGroup("codeReview", NotificationDisplayType.BALLOON, true);
            Notification notification = notificationGroup.createNotification("导出" + fullPath + "成功", MessageType.INFO);
            Notifications.Bus.notify(notification);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initCrQuestionList() {
        questionTable.setModel(CrQuestionHouse.TABLE_MODEL);
        questionTable.setEnabled(false);
    }

    private JPopupMenu createPopupWhenClickRightMouse(int row) {

        // 删除
        JMenuItem deleteItem = CommonUtils.buildJMenuItem("删除", "/img/delete.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestionHouse.delete(row);
            }
        });

        // 编辑
        JMenuItem editItem = CommonUtils.buildJMenuItem("详情", "/img/detail.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuestionDetailDialog(row);
            }
        });

        // 解决
        JMenuItem solveItem = CommonUtils.buildJMenuItem("解决", "/img/solve.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestion question = CrQuestionHouse.getCrQuestionList().get(row);
                CrQuestionUtils.solveQuestion(row, question);
            }
        });
        // 右键框
        return CommonUtils.buildJPopupMenu(deleteItem, editItem, solveItem);
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
