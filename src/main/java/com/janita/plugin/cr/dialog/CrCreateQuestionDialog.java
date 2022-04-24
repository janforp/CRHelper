package com.janita.plugin.cr.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.janita.plugin.common.constant.DataToInit;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.exception.PluginRuntimeException;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.util.CrQuestionUtils;
import com.janita.plugin.cr.window.table.CrQuestionHouse;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * 创建一个提问题的框
 *
 * @author zhucj
 * @since 20220415
 */
public class CrCreateQuestionDialog extends JDialog {

    /**
     * 工程
     */
    private final Project project;

    /**
     * 当前项目的开发者
     */
    private Set<String> developerSet;

    /**
     * 问题
     */
    private CrQuestion question;

    /**
     * 主框架
     */
    private JPanel contentPane;

    /**
     * 保存
     */
    private JButton buttonOK;

    /**
     * 取消
     */
    private JButton buttonCancel;

    /**
     * 问题类型列表
     */
    private JComboBox<String> questionTypeBox;

    /**
     * 问题代码
     */
    private JTextArea questionCodeArea;

    /**
     * 建议写法
     */
    private JTextArea betterCodeArea;

    /**
     * 描述
     */
    private JTextArea descArea;

    /**
     * 指派给的人员列表
     */
    private JComboBox<String> selectAssignBox;

    /**
     * 级别
     */
    private JComboBox<String> levelBox;

    /**
     * 解决按钮
     */
    private JButton solveButton;

    /**
     * 状态下拉
     */
    private JComboBox<String> stateBox;

    /**
     * 手动指定
     */
    private JButton manualAssignButton;

    /**
     * 手动指定人
     */
    private JTextField manualAssignerField;

    /**
     * 修改还是添加
     */
    private boolean update;

    /**
     * 是否手动指定
     */
    private boolean manualAssign;

    /**
     * 被编辑的序号
     */
    private Integer editIndex;

    /**
     * 新建
     */
    public CrCreateQuestionDialog(Project project, Set<String> developerSet) {
        this.developerSet = developerSet;
        this.update = false;
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveQuestion();
            }
        });
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestionUtils.solveQuestion(editIndex, question);
                closeDialog();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeDialog();
            }
        });

        manualAssignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (manualAssign) {
                    return;
                }
                setToManualAssign();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeDialog();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                closeDialog();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void setToManualAssign() {
        selectAssignBox.setEnabled(false);
        manualAssignerField.setEnabled(true);
        manualAssign = true;
    }

    /**
     * 在列表上编辑
     */
    public CrCreateQuestionDialog(Integer index, Project project, Set<String> developerSet) {
        this(project, developerSet);
        this.update = true;
        this.editIndex = index;
    }

    private void saveQuestion() {
        rebuildQuestionWhenSave();
        if (update) {
            CrQuestionHouse.update(editIndex, question);
        } else {
            CrQuestionHouse.add(question);
        }
        dispose();
    }

    private void rebuildQuestionWhenSave() {
        question.setType((String) questionTypeBox.getSelectedItem());
        question.setState((String) stateBox.getSelectedItem());
        question.setLevel((String) levelBox.getSelectedItem());
        question.setAssignTo(getAssigner());
        question.setBetterCode(betterCodeArea.getText());
        question.setDescription(descArea.getText());
    }

    private String getAssigner() {
        String assignTo;
        if (manualAssign) {
            assignTo = manualAssignerField.getText();
        } else {
            assignTo = (String) selectAssignBox.getSelectedItem();
        }
        if (PluginConstant.PLEASE_MANUAL_ASSIGN.equals(assignTo)) {
            throw new PluginRuntimeException("请手动指派");
        }
        return assignTo;
    }

    private void closeDialog() {
        dispose();
    }

    public void open(CrQuestion question) {
        this.question = question;
        initBox(question.getAssignTo());

        questionTypeBox.setSelectedItem(question.getType() != null ? question.getType() : DataToInit.QUESTION_TYPE_LIST.get(0));
        stateBox.setSelectedItem(question.getState() != null ? question.getState() : CrQuestionState.UNSOLVED.getDesc());
        questionCodeArea.setText(question.getQuestionCode());
        questionCodeArea.setEditable(false);
        betterCodeArea.setText(question.getBetterCode());
        descArea.setText(question.getDescription());
        selectAssignBox.setSelectedItem(question.getAssignTo() != null ? question.getAssignTo() : new ArrayList<>(developerSet).get(0));
        levelBox.setSelectedItem(question.getLevel() != null ? question.getLevel() : DataToInit.LEVEL_LIST.get(0));
        pack();
        setTitle(question.getProjectName() + "-" + question.getCreateGitBranchName() + "-" + question.getFileName());
        setMinimumSize(new Dimension(800, 600));
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(this.project));
        manualAssignerField.setEnabled(false);

        boolean isAdd = !update;
        if (isAdd) {
            // 如果是添加，则
            // 1.解决按钮不可点击
            // 2.状态下拉显示未解决，并且不能选择
            solveButton.setEnabled(false);
            stateBox.setSelectedItem("未解决");
            stateBox.setEnabled(false);
        }

        // 指派下拉只有一个数据
        if (developerSet.size() == 1 && developerSet.contains(PluginConstant.PLEASE_MANUAL_ASSIGN)) {
            // 指派下拉不可用，并且手动指派
            setToManualAssign();
        }
        setVisible(true);
    }

    /**
     * 时候可以去远程拉去
     *
     * 建议，性能， 缺陷， 规范
     *
     * @param assignTo 指定人
     */
    private void initBox(String assignTo) {
        for (String type : DataToInit.QUESTION_TYPE_LIST) {
            questionTypeBox.addItem(type);
        }

        if (StringUtils.isNotBlank(assignTo)) {
            developerSet = ObjectUtils.defaultIfNull(developerSet, new HashSet<>(0));
            developerSet.add(assignTo);
        }

        for (String developer : developerSet) {
            selectAssignBox.addItem(developer);
        }

        for (String level : DataToInit.LEVEL_LIST) {
            levelBox.addItem(level);
        }

        for (CrQuestionState state : CrQuestionState.values()) {
            stateBox.addItem(state.getDesc());
        }
    }
}
