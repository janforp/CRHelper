package com.janita.plugin.demo.layout;

import javax.swing.*;
import java.awt.*;

/**
 * CardLayoutDemo
 *
 * @author zhucj
 * @since 20220324
 */
public class CardLayoutDemo {

    public static void main(String[] agrs) {
        JFrame frame = new JFrame("Java第五个程序");    //创建Frame窗口
        JPanel p1 = new JPanel();    //面板1
        JPanel p2 = new JPanel();    //面板2
        JPanel cards = new JPanel(new CardLayout());    //卡片式布局的面板
        p1.add(new JButton("登录按钮"));
        p1.add(new JButton("注册按钮"));
        p1.add(new JButton("找回密码按钮"));
        p2.add(new JTextField("用户名文本框", 20));
        p2.add(new JTextField("密码文本框", 20));
        p2.add(new JTextField("验证码文本框", 20));
        cards.add(p1, "card1");    //向卡片式布局面板中添加面板1
        cards.add(p2, "card2");    //向卡片式布局面板中添加面板2
        CardLayout cl = (CardLayout) (cards.getLayout());

        // TODO 通过该方法决定显示哪个 card
        cl.show(cards, "card2");    //调用show()方法显示面板2
        //cl.show(cards, "card1");    //调用show()方法显示面板2
        frame.add(cards);
        frame.setBounds(300, 200, 400, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}