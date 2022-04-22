package com.janita.plugin.demo.layout;

import javax.swing.*;
import java.awt.*;

/**
 * http://c.biancheng.net/view/1212.html
 *
 * 网格包布局管理器
 * GridBagLayout（网格包布局管理器）是在网格基础上提供复杂的布局，是最灵活、 最复杂的布局管理器。GridBagLayout 不需要组件的尺寸一致，允许组件扩展到多行多列。每个 GridBagLayout 对象都维护了一组动态的矩形网格单元，每个组件占一个或多个单元，所占有的网格单元称为组件的显示区域。
 *
 * GridBagLayout 所管理的每个组件都与一个 GridBagConstraints 约束类的对象相关。这个约束类对象指定了组件的显示区域在网格中的位置，以及在其显示区域中应该如何摆放组件。除了组件的约束对象，GridBagLayout 还要考虑每个组件的最小和首选尺寸，以确定组件的大小。
 *
 * 为了有效地利用网格包布局管理器，在向容器中添加组件时，必须定制某些组件的相关约束对象。GridBagConstraints 对象的定制是通过下列变量实现的。
 * 1. gridx 和 gridy
 * 用来指定组件左上角在网格中的行和列。容器中最左边列的 gridx 为 0，最上边行的 gridy 为 0。这两个变量的默认值是 GridBagConstraints.RELATIVE，表示对应的组件将放在前一个组件的右边或下面。
 * 2. gridwidth 和 gridheight
 * 用来指定组件显示区域所占的列数和行数，以网格单元而不是像素为单位，默认值为 1。
 * 3. fill
 * 指定组件填充网格的方式，可以是如下值：GridBagConstraints.NONE（默认值）、GridBagConstraints.HORIZONTAL（组件横向充满显示区域，但是不改变组件高度）、GridBagConstraints.VERTICAL（组件纵向充满显示区域，但是不改变组件宽度）以及 GridBagConstraints.BOTH（组件横向、纵向充满其显示区域）。
 * 4. ipadx 和 ipady
 * 指定组件显示区域的内部填充，即在组件最小尺寸之外需要附加的像素数，默认值为 0。
 * 5. insets
 * 指定组件显示区域的外部填充，即组件与其显示区域边缘之间的空间，默认组件没有外部填充。
 * 6. anchor
 * 指定组件在显示区域中的摆放位置。可选值有 GridBagConstraints.CENTER（默认值）、GridBagConstraints.NORTH、GridBagConstraints.
 * NORTHEAST、GridBagConstraints.EAST、GridBagConstraints.SOUTH、GridBagConstraints.SOUTHEAST、GridBagConstraints.WEST、GridBagConstraints.SOUTHWEST 以及 GridBagConstraints.NORTHWEST。
 * 7. weightx 和 weighty
 * 用来指定在容器大小改变时，增加或减少的空间如何在组件间分配，默认值为 0，即所有的组件将聚拢在容器的中心，多余的空间将放在容器边缘与网格单元之间。weightx 和 weighty 的取值一般在 0.0 与 1.0 之间，数值大表明组件所在的行或者列将获得更多的空间。
 *
 * @author zhucj
 * @since 20220324
 */
public class GridBagLayoutDemo {

    public static void makeButton(String title, JFrame frame, GridBagLayout gridBagLayout, GridBagConstraints constraints) {
        JButton button = new JButton(title);    //创建Button对象
        gridBagLayout.setConstraints(button, constraints);
        frame.add(button);
    }

    public static void main(String[] agrs) {
        JFrame frame = new JFrame("拨号盘");

        //创建GridBagLayout布局管理器
        GridBagLayout gridBagLayout = new GridBagLayout();
        //使用GridBagLayout布局管理器
        frame.setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();
        //组件填充显示区域
        constraints.fill = GridBagConstraints.BOTH;
        //恢复默认值
        constraints.weightx = 0.0;
        // 网格宽度
        //结束行
        constraints.gridwidth = GridBagConstraints.REMAINDER;

        JTextField numberField = new JTextField("13612345678");
        gridBagLayout.setConstraints(numberField, constraints);
        frame.add(numberField);

        // 指定组件的分配区域
        constraints.weightx = 0.5;
        constraints.weighty = 0.2;
        // 网格宽度
        constraints.gridwidth = 1;
        //调用方法，添加按钮组件
        makeButton("7", frame, gridBagLayout, constraints);
        makeButton("8", frame, gridBagLayout, constraints);
        //结束行
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        makeButton("9", frame, gridBagLayout, constraints);
        //重新设置gridWidth的值
        constraints.gridwidth = 1;

        makeButton("4", frame, gridBagLayout, constraints);
        makeButton("5", frame, gridBagLayout, constraints);
        //结束行
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        makeButton("6", frame, gridBagLayout, constraints);
        constraints.gridwidth = 1;

        makeButton("1", frame, gridBagLayout, constraints);
        makeButton("2", frame, gridBagLayout, constraints);
        //结束行
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        makeButton("3", frame, gridBagLayout, constraints);
        constraints.gridwidth = 1;

        makeButton("返回", frame, gridBagLayout, constraints);
        //结束行
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        makeButton("拨号", frame, gridBagLayout, constraints);
        constraints.gridwidth = 1;
        //设置容器大小
        frame.setBounds(400, 400, 400, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
