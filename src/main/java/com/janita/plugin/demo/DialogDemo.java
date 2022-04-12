package com.janita.plugin.demo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 弹出对话框
 * 用户输入用户名跟密码
 * 点击确认之后拿到用户名跟密码并且打印出来，之后关闭对话框
 */
public class DialogDemo extends JDialog {

    private JPanel contentPane;

    private JButton buttonOK;

    private JButton buttonCancel;

    private JTextField nameField;

    private JPasswordField passwordField;

    public DialogDemo() {
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

    private void onOK() {
        String name = nameField.getText();
        char[] fieldPassword = passwordField.getPassword();
        String password = new String(fieldPassword);
        System.out.println(name + password);
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}
