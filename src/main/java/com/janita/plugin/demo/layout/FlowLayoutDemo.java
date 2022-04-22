package com.janita.plugin.demo.layout;

import javax.swing.*;
import java.awt.*;

/**
 * FlowLayoutDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class FlowLayoutDemo {

    public static void main(String[] agrs) {
        JFrame jFrame = new JFrame("Java第四个GUI程序");    //创建Frame窗口
        JPanel jPanel = new JPanel();    //创建面板
        JButton btn1 = new JButton("1");    //创建按钮
        JButton btn2 = new JButton("2");
        JButton btn3 = new JButton("3");
        JButton btn4 = new JButton("4");
        JButton btn5 = new JButton("5");
        JButton btn6 = new JButton("6");
        JButton btn7 = new JButton("7");
        JButton btn8 = new JButton("8");
        JButton btn9 = new JButton("9");
        jPanel.add(btn1);    //面板中添加按钮
        jPanel.add(btn2);
        jPanel.add(btn3);
        jPanel.add(btn4);
        jPanel.add(btn5);
        jPanel.add(btn6);
        jPanel.add(btn7);
        jPanel.add(btn8);
        jPanel.add(btn9);
        //向JPanel添加FlowLayout布局管理器，将组件间的横向和纵向间隙都设置为20像素
        jPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 20));
        jPanel.setBackground(Color.gray);    //设置背景色
        jFrame.add(jPanel);    //添加面板到容器
        jFrame.setBounds(300, 200, 300, 150);    //设置容器的大小
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
