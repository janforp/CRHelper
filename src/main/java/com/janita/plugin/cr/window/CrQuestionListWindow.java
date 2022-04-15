package com.janita.plugin.cr.window;

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
import com.janita.plugin.cr.remote.CrQuestionQueryRequest;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * 刷新按钮
     */
    private JButton refreshButton;

    /**
     * 项目
     */
    private JComboBox<String> projectBox;

    /**
     * 状态
     */
    private JComboBox<String> stateBox;

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
        getRootPane().setDefaultButton(closeCancel);

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

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String projectName = (String) projectBox.getSelectedItem();
                String state = (String) stateBox.getSelectedItem();
                CrQuestionQueryRequest request = new CrQuestionQueryRequest(new HashSet<>(Collections.singletonList(state)), new HashSet<>(Collections.singletonList(projectName)));
                CrQuestionHouse.refresh(request);
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

    @SuppressWarnings("all")
    private void export() {
        List<CrQuestion> crQuestionList = CrQuestionHouse.getCrQuestionList();
        if (crQuestionList == null || crQuestionList.size() == 0) {
            CommonUtils.showNotification("没有任何 Code review 内容！", MessageType.ERROR);
            return;
        }
        VirtualFile virtualFile = FileChooser.chooseFile(FileChooserDescriptorFactory.createSingleFolderDescriptor(), project, project.getBaseDir());
        if (virtualFile == null) {
            return;
        }
        String path = virtualFile.getPath();
        String fileName = DateUtils.getCurrentTimeForFileName() + "-CR.md";
        String fullPath = path + File.separator + DateUtils.getCurrentTimeForFileName() + "-CR.md";
        MDFreeMarkProcessor processor = new MDFreeMarkProcessor();
        try {
            List<CrQuestion> exportList = CrQuestionUtils.processBeforeExport(crQuestionList);
            processor.process(fileName, fullPath, exportList);
            CommonUtils.showNotification("导出成功", MessageType.INFO);
        } catch (Exception e) {
            CommonUtils.showNotification("导出失败，请联系相关人员处理", MessageType.ERROR);
        }
    }

    private void initCrQuestionList() {
        questionTable.setModel(CrQuestionHouse.TABLE_MODEL);
        questionTable.setEnabled(false);
        Set<String> projectNameSet = CommonUtils.getAllProjectName(this.project);
        CrQuestionQueryRequest request = new CrQuestionQueryRequest(null, projectNameSet);
        CrQuestionHouse.refresh(request);

        for (String state : CrCreateQuestionDialog.STATE_LIST) {
            stateBox.addItem(state);
        }
        for (String projectName : projectNameSet) {
            projectBox.addItem(projectName);
        }
    }

    private JPopupMenu createPopupWhenClickRightMouse(int row) {

        // 删除
        JMenuItem deleteItem = CommonUtils.buildJMenuItem("删除", "/img/delete.png", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestion question = CrQuestionHouse.getCrQuestionList().get(row);
                CrQuestionHouse.delete(row, question);
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
        return CommonUtils.buildJPopupMenu(editItem, solveItem, deleteItem);
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
