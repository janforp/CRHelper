package com.janita.plugin.cr.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.janita.plugin.cr.domain.CrQuestion;
import com.janita.plugin.cr.util.CrQuestionUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;

public class CrCreateQuestionDialog extends JDialog {

    /**
     * 问题类型
     */
    private static final List<String> QUESTION_TYPE_LIST = Arrays.asList("建议", "性能", "缺陷", "规范");

    /**
     * 账户
     */
    private static final List<String> ACCOUNT_LIST = Arrays.asList("王尚飞", "朱晨剑", "张丹", "杨艳斌");

    /**
     * 问题级别
     */
    private static final List<String> LEVEL_LIST = Arrays.asList("1", "2", "3", "4", "5");

    /**
     * 状态
     */
    private static final List<String> STATE_LIST = Arrays.asList("未解决", "已解决", "重复问题", "已关闭");

    /**
     * 工程
     */
    private final Project project;

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
    private JComboBox<String> toAccountBox;

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
     * 修改还是添加
     */
    private Boolean update;

    /**
     * 被编辑的序号
     */
    private Integer editIndex;

    /**
     * 新建
     */
    public CrCreateQuestionDialog(Project project) {
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
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * 在列表上编辑
     */
    public CrCreateQuestionDialog(Integer index, Project project) {
        this(project);
        this.update = true;
        this.editIndex = index;
    }

    private void saveQuestion() {
        rebuildQuestionWhenSave();
        CrQuestionUtils.saveQuestion(update, editIndex, question);
        dispose();
    }

    private void rebuildQuestionWhenSave() {
        question.setType((String) questionTypeBox.getSelectedItem());
        question.setState((String) stateBox.getSelectedItem());
        question.setBetterCode(betterCodeArea.getText());
        question.setDesc(descArea.getText());
        question.setLevel((String) levelBox.getSelectedItem());
        question.setToAccount((String) toAccountBox.getSelectedItem());
    }

    private void onCancel() {
        dispose();
    }

    public void open(CrQuestion question) {
        this.question = question;
        initBox();

        questionTypeBox.setSelectedItem(question.getType() != null ? question.getType() : QUESTION_TYPE_LIST.get(0));
        stateBox.setSelectedItem(question.getState() != null ? question.getState() : STATE_LIST.get(0));
        questionCodeArea.setText(question.getQuestionCode());
        questionCodeArea.setEditable(false);
        betterCodeArea.setText(question.getBetterCode());
        descArea.setText(question.getDesc());
        levelBox.setSelectedItem(question.getLevel() != null ? question.getLevel() : LEVEL_LIST.get(0));
        pack();
        setTitle(question.getProjectName() + "-" + question.getGitBranchName() + "-" + question.getClassName() + "-" + question.getLineFrom() + "到" + question.getLineTo());
        setMinimumSize(new Dimension(800, 600));
        //两个屏幕处理出现问题，跳到主屏幕去了
        setLocationRelativeTo(WindowManager.getInstance().getFrame(this.project));
        setVisible(true);
    }

    /**
     * 时候可以去远程拉去
     *
     * 建议，性能， 缺陷， 规范
     */
    private void initBox() {
        for (String type : QUESTION_TYPE_LIST) {
            questionTypeBox.addItem(type);
        }

        for (String account : ACCOUNT_LIST) {
            toAccountBox.addItem(account);
        }
        for (String level : LEVEL_LIST) {
            levelBox.addItem(level);
        }

        for (String state : STATE_LIST) {
            stateBox.addItem(state);
        }
    }
}
