package com.janita.plugin.demo.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.janita.plugin.common.constant.DataToInit;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.enums.CrQuestionState;
import com.janita.plugin.common.util.JSwingUtils;
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
public class CrCreateQuestionDialogBack extends JDialog {

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

    private JScrollPane questionPanel;

    /**
     * 建议写法
     */
    private JTextArea betterCodeArea;

    private JScrollPane betterPanel;

    /**
     * 描述
     */
    private JTextArea descArea;

    private JScrollPane descPanel;

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
    public CrCreateQuestionDialogBack(Project project, Set<String> developerSet) {
        this.developerSet = developerSet;
        this.update = false;
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinAndMaxSize();

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addListener();
    }

    private void setToManualAssign() {
        selectAssignBox.setEnabled(false);
        manualAssignerField.setEnabled(true);
        manualAssign = true;
    }

    private void addListener() {
        // 点击左上角的 X 关闭
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveQuestion();
            }
        });
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CrQuestionUtils.solveQuestion(project, editIndex, question);
                dispose();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
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
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * 在列表上编辑
     */
    public CrCreateQuestionDialogBack(Integer index, Project project, Set<String> developerSet) {
        this(project, developerSet);
        this.update = true;
        this.editIndex = index;
    }

    private void saveQuestion() {
        rebuildQuestionWhenSave();
        Pair<Boolean, String> pair = checkBeforeSave();
        if (!pair.getLeft()) {
            JSwingUtils.showErrorDialog("", pair.getRight());
            return;
        }
        if (update) {
            CrQuestionHouse.update(editIndex, question, false);
        } else {
            CrQuestionHouse.add(question, false);
        }
        dispose();
    }

    private Pair<Boolean, String> checkBeforeSave() {
        if (StringUtils.isBlank(question.getType())) {
            return Pair.of(false, "请选择问题类型");
        }
        if (StringUtils.isBlank(question.getState())) {
            return Pair.of(false, "请选择问题状态");
        }
        if (StringUtils.isBlank(question.getAssignTo())) {
            return Pair.of(false, "请把该问题指派给他人或者自己");
        }
        if (StringUtils.isBlank(question.getLevel())) {
            return Pair.of(false, "请选择问题级别");
        }
        if (StringUtils.isBlank(question.getBetterCode()) && StringUtils.isBlank(question.getDescription())) {
            return Pair.of(false, "建议写法跟描述至少写一项");
        }
        return Pair.of(true, "");
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
        return assignTo;
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

    private void setMinAndMaxSize() {
        // 3个输入框有最小高度
        questionPanel.setMinimumSize(new Dimension(100, 150));
        betterPanel.setMinimumSize(new Dimension(100, 150));
        descPanel.setMinimumSize(new Dimension(100, 100));

        questionPanel.setMaximumSize(new Dimension(100, 200));
        betterPanel.setMaximumSize(new Dimension(100, 200));
        descPanel.setMaximumSize(new Dimension(100, 150));
    }

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
