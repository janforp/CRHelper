package com.janita.plugin.cr.window;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.ToolWindow;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.ButtonType;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.progress.AbstractProgressTask;
import com.janita.plugin.common.progress.ProgressUtils;
import com.janita.plugin.common.util.CommonUtils;
import com.janita.plugin.common.util.CompatibleUtils;
import com.janita.plugin.common.util.DateUtils;
import com.janita.plugin.common.util.JSwingUtils;
import com.janita.plugin.common.util.SingletonBeanFactory;
import com.janita.plugin.cr.dialog.CrQuestionEditDialog;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.domain.CrQuestionQueryRequest;
import com.janita.plugin.cr.export.MDFreeMarkProcessor;
import com.janita.plugin.cr.export.vo.CrQuestionExportVO;
import com.janita.plugin.cr.util.CrQuestionUtils;
import com.janita.plugin.cr.window.table.CrQuestionHouse;
import com.janita.plugin.cr.window.table.CrQuestionTable;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
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
     * 当前项目列表
     */
    private final List<String> projectNameList;

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
        this.project = project;
        this.projectNameList = Lists.newArrayList(CompatibleUtils.getAllProjectNameFromGitFirstThenLocal(project));
        JSwingUtils.setTableType(questionTable);
        initCrQuestionList();
        setContentPane(contentPane);
        getRootPane().setDefaultButton(closeCancel);
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        addListener(toolWindow);
    }

    private void addListener(ToolWindow toolWindow) {
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
                query();
            }
        });

        questionTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                super.mouseClicked(mouseEvent);
                Point p = mouseEvent.getPoint();
                int row = questionTable.rowAtPoint(p);
                questionTable.setRowSelectionInterval(row, row);
                ButtonType buttonType = JSwingUtils.getMouseButtonType(mouseEvent);
                if (buttonType == ButtonType.LEFT) {
                    // 左键点击一下，打开对应文件，并且选中文本
                    CrQuestion question = CrQuestionTable.getCrQuestionList().get(row);
                    CommonUtils.openFileAndLocationToText(project, question.getFilePath(), question.getFileName(), question.getOffsetStart(), question.getOffsetEnd(), question.getBetterCode());
                }
                if (mouseEvent.getClickCount() == 2) {
                    // 双击
                    showQuestionDetailDialog(row);
                }
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toolWindow.hide(null);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        projectBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                query();
            }
        });
        stateBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                query();
            }
        });
    }

    private void query() {
        String projectName = (String) projectBox.getSelectedItem();
        Set<CrQuestionState> stateSet = getStateByUserSelect();
        CrQuestionQueryRequest request = new CrQuestionQueryRequest(projectName, stateSet);
        ProgressUtils.showProgress(project, "Querying", new AbstractProgressTask() {
            @Override
            public void doProcess() {
                try {
                    CrQuestionHouse.rerenderTable(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Set<CrQuestionState> getStateByUserSelect() {
        String stateDesc = (String) stateBox.getSelectedItem();
        if (StringUtils.equals(stateDesc, CrQuestionState.TOTAL)) {
            return Sets.newHashSet(CrQuestionState.values());
        }
        CrQuestionState state = CrQuestionState.getByDesc(stateDesc);
        return Sets.newHashSet(state);
    }

    @SuppressWarnings("all")
    private void export() {
        List<CrQuestion> crQuestionList = CrQuestionTable.getCrQuestionList();
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
            List<CrQuestionExportVO> exportList = CrQuestionUtils.processBeforeExport(crQuestionList);
            processor.process(fileName, fullPath, exportList);
            CommonUtils.openFile(project, fullPath);
            CommonUtils.showNotification("导出成功", MessageType.INFO);
        } catch (Exception e) {
            CommonUtils.showNotification("导出失败，请联系相关人员处理", MessageType.ERROR);
        }
    }

    private void initCrQuestionList() {
        questionTable.setModel(CrQuestionTable.TABLE_MODEL);
        questionTable.setEnabled(false);
        stateBox.addItem(CrQuestionState.TOTAL);
        for (CrQuestionState state : CrQuestionState.values()) {
            stateBox.addItem(state.getDesc());
        }
        for (String projectName : projectNameList) {
            projectBox.addItem(projectName);
        }
    }

    private void showQuestionDetailDialog(int row) {
        CrQuestion question = CrQuestionTable.getCrQuestionList().get(row);
        Pair<Boolean, Set<String>> pair = SingletonBeanFactory.getCrQuestionService().queryAssignName(question.getProjectName());
        CrQuestionEditDialog crQuestionEditDialog = new CrQuestionEditDialog(project, question, pair.getRight(), false, row);
        crQuestionEditDialog.open();
    }
}
