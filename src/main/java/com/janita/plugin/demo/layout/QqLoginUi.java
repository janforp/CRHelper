package com.janita.plugin.demo.layout;

import javax.swing.*;
import java.awt.*;

/**
 * QqLoginUi
 *
 * @author zhucj
 * @since 20220324
 */
@SuppressWarnings("all")
public class QqLoginUi {

    public static void main(String args[]) {
        QqLoginUi ui = new QqLoginUi();
        ui.show();
    }

    public void show() {

        /*
         * 窗体的基本设置
         */
        JFrame jf = new JFrame();
        jf.setSize(450, 300);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(3);
        jf.setResizable(false);
        /*
         * 生成窗体中的各种组件
         */

        //组件1 是界面上的QQ蓝色面板图像，图像我们把它放在JLabel类对象上
        ImageIcon bigBluePic = new ImageIcon(this.getClass().getResource("/img/QQ面板.png"));
        JLabel bigBlueBoard = new JLabel(bigBluePic);

        //组件2 是界面上的QQ企鹅图像，同理图像我们把它放在JLabel类对象上
        ImageIcon qqImage = new ImageIcon(this.getClass().getResource("/img/QQ头像.png"));
        JLabel qqImageComponent = new JLabel(qqImage);

        //组件3是用户的账号输入框
        JTextField qqAccountNumberInput = new JTextField();

        //组件4是用户的账号输入框右边的提示标签
        JLabel qqAccountNumberInputLabel = new JLabel("用户账号");

        //组件5是用户的密码输入框
        JTextField qqPasswordInput = new JTextField();

        //组件6是用户的密码输入框右边的提示标签
        JLabel qqPasswordInputLabel = new JLabel("用户密码");

        //组件7是用户的“记住密码”的勾选键
        JCheckBox rememberPwdCheckBox = new JCheckBox("记住密码");

        //组件8是用户的“自动登录”的勾选键
        JCheckBox autoLoginCheckBox = new JCheckBox("自动登录");

        //组件9是用户的“安全登录”的按键
        JButton saveLoginButton = new JButton("安全登录");

        /*
         * 对窗体进行布局
         */

        //实例化布局对象
        GridBagLayout gridBagLayout = new GridBagLayout();
        //jf窗体对象设置为GridBagLayout布局
        jf.setLayout(gridBagLayout);
        //实例化这个对象用来对组件进行管理
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        //该方法是为了设置如果组件所在的区域比组件本身要大时的显示情况
        //NONE：不调整组件大小。
        //HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        //VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        //BOTH：使组件完全填满其显示区域。
        gridBagConstraints.fill = GridBagConstraints.BOTH;


        /*
         * 分别对组件进行设置
         */
        //组件1(gridx,gridy)组件的左上角坐标，gridwidth，gridheight：组件占用的网格行数和列数

        // 上面的大蓝色图片
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 4;
        gridBagLayout.setConstraints(bigBlueBoard, gridBagConstraints);

        //组件2 左下角的 qq 头像
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 4;
        gridBagLayout.setConstraints(qqImageComponent, gridBagConstraints);

        // 账号输入框
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(qqAccountNumberInput, gridBagConstraints);

        // 用户账号标签
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(qqAccountNumberInputLabel, gridBagConstraints);

        // 密码输入框
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(qqPasswordInput, gridBagConstraints);

        // 用户密码标签
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(qqPasswordInputLabel, gridBagConstraints);

        // 记住密码勾选框
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(rememberPwdCheckBox, gridBagConstraints);

        // 自动登陆勾选框
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(autoLoginCheckBox, gridBagConstraints);

        // 安全登陆按钮
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(saveLoginButton, gridBagConstraints);

        /*
         * 把所有组件加入jf窗体对象中去
         */
        jf.add(bigBlueBoard);
        jf.add(qqImageComponent);
        jf.add(qqAccountNumberInput);
        jf.add(qqAccountNumberInputLabel);
        jf.add(qqPasswordInput);
        jf.add(qqPasswordInputLabel);
        jf.add(rememberPwdCheckBox);
        jf.add(autoLoginCheckBox);
        jf.add(saveLoginButton);

        jf.setVisible(true);

    }
}