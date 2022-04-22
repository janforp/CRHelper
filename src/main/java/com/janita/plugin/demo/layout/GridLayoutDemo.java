package com.janita.plugin.demo.layout;

import javax.swing.*;
import java.awt.*;

/**
 * GridLayoutDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class GridLayoutDemo {

    public static void main(String[] args) {
        JFrame frame = new JFrame("计算器");

        //创建面板
        JPanel panel = new JPanel();

        //指定面板的布局为GridLayout，4行4列，间隙为5
        panel.setLayout(new GridLayout(4, 4, 5, 5));

        /* 添加按钮
         * 7    8   9   /
         * 4    5   6   *
         * 1    2   3   -
         * 0    .   =   +
         *
         * 按顺序从左到右从上到下展示
         */
        panel.add(new JButton("7"));
        panel.add(new JButton("8"));
        panel.add(new JButton("9"));
        panel.add(new JButton("/"));
        panel.add(new JButton("4"));
        panel.add(new JButton("5"));
        panel.add(new JButton("6"));
        panel.add(new JButton("*"));
        panel.add(new JButton("1"));
        panel.add(new JButton("2"));
        panel.add(new JButton("3"));
        panel.add(new JButton("-"));
        panel.add(new JButton("0"));
        panel.add(new JButton("."));
        panel.add(new JButton("="));
        panel.add(new JButton("+"));

        frame.add(panel);    //添加面板到容器
        //frame.setBounds(300, 200, 200, 150);
        frame.setSize(new Dimension(600, 600));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
