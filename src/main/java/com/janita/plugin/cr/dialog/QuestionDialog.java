package com.janita.plugin.cr.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.janita.plugin.cr.domain.CrQuestion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class QuestionDialog extends JDialog {

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
    private JComboBox<String> questionTypeList;

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

    public QuestionDialog(Project project) {
        this.project = project;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
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

    private void onOK() {
        System.out.println(question);
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void open(CrQuestion question) {
        this.question = question;
        initQuestionTypeList();
        questionCodeArea.setText(question.getQuestionCode());
        questionCodeArea.setEditable(false);
        betterCodeArea.setText("建议写法");
        descArea.setText("描述");
        pack();
        setTitle(question.getGitBranchName() + "-" + question.getClassName());
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
    private void initQuestionTypeList() {
        questionTypeList.addItem("建议");
        questionTypeList.addItem("性能");
        questionTypeList.addItem("缺陷");
        questionTypeList.addItem("规范");
    }
}
