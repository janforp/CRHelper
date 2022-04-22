package com.janita.plugin.demo.layout;

import javax.swing.*;
import java.awt.*;

/**
 * 盒布局管理器
 * BoxLayout（盒布局管理器）通常和 Box 容器联合使用，Box 类有以下两个静态方法。
 * createHorizontalBox()：返回一个 Box 对象，它采用水平 BoxLayout，即 BoxLayout 沿着水平方向放置组件，让组件在容器内从左到右排列。
 * createVerticalBox()：返回一个 Box 对象，它采用垂直 BoxLayout，即 BoxLayout 沿着垂直方向放置组件，让组件在容器内从上到下进行排列。
 *
 * Box 还提供了用于决定组件之间间隔的静态方法，如表 1 所示。
 *
 * 表1 Box类设置组件间隔的静态方法
 * 网格包布局	说明
 * static Component createHorizontalGlue()	创建一个不可见的、可以被水平拉伸和收缩的组件
 * static Component createVerticalGlue()	创建一个不可见的、可以被垂直拉伸和收缩的组件
 * static Component createHorizontalStrut(int width)	创建一个不可见的、固定宽度的组件
 * static Component createVerticalStrut(int height)	创建一个不可见的、固定高度的组件
 * static Component createRigidArea(Dimension d)	创建一个不可见的、总是具有指定大小的组件
 * BoxLayout 类只有一个构造方法，如下所示。
 * 纯文本复制
 * BoxLayout(Container c,int axis)
 *
 * @author zhucj
 * @since 20220324
 */
public class BoxLayoutDemo {

    public static void main(String[] agrs) {
        JFrame frame = new JFrame("Java示例程序");
        Box b1 = Box.createHorizontalBox();    //创建横向Box容器
        Box b2 = Box.createVerticalBox();    //创建纵向Box容器
        frame.add(b1);    //将外层横向Box添加进窗体
        b1.add(Box.createVerticalStrut(200));    //添加高度为200的垂直框架
        b1.add(new JButton("西"));    //添加按钮1
        b1.add(Box.createHorizontalStrut(140));    //添加长度为140的水平框架
        b1.add(new JButton("东"));    //添加按钮2
        b1.add(Box.createHorizontalGlue());    //添加水平胶水
        b1.add(b2);    //添加嵌套的纵向Box容器
        //添加宽度为100，高度为20的固定区域
        b2.add(Box.createRigidArea(new Dimension(100, 20)));
        b2.add(new JButton("北"));    //添加按钮3
        b2.add(Box.createVerticalGlue());    //添加垂直组件
        b2.add(new JButton("南"));    //添加按钮4
        b2.add(Box.createVerticalStrut(40));    //添加长度为40的垂直框架
        //设置窗口的关闭动作、标题、大小位置以及可见性等
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 400, 200);
        frame.setVisible(true);
    }
}
