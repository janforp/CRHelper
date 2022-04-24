package com.janita.plugin.demo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.*;

public class JTableTestFrame extends JFrame {

    private JPanel contentPane;

    private DefaultTableModel defaultTableModel;

    private JTable table;

    private TableColumn column;

    Toolkit toolkit = Toolkit.getDefaultToolkit();

    private int DEFAULE_WIDTH = 1000;

    private int DEFAULE_HEIGH = 600;

    int Location_x = (int) (toolkit.getScreenSize().getWidth() - DEFAULE_WIDTH) / 2;

    int Location_y = (int) (toolkit.getScreenSize().getHeight() - DEFAULE_HEIGH) / 2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JTableTestFrame frame = new JTableTestFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public JTableTestFrame() {

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());

        String[] n1 = { "", "", "", "", "", "", "", "", "", "", "", "", "" };
        Object[][] p = { n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1,
                n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1, n1,
                n1, };
        String[] n = { "序号", "姓名", "学号", "语文", "数学", "英语", "政治", "历史", "地理", "物理", "化学", "生物", "总分" };

        defaultTableModel = new DefaultTableModel(p, n); // 用双数组创建DefaultTableModel对象
        table = new JTable(defaultTableModel);// 创建表格组件
        JTableHeader head = table.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 18));// 设置表格字体
        table.setRowHeight(18);// 设置表格行宽

        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
        for (int i = 0; i < 13; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            }
        }

        // 设置表格间隔色
        DefaultTableCellRenderer ter = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                // table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
                if (row % 2 == 0) {
                    setBackground(Color.pink);
                } else if (row % 2 == 1) {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        for (int i = 0; i < 13; i++) {
            table.getColumn(n[i]).setCellRenderer(ter);
        }

        JScrollPane scrollPane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);// 创建滚动条组件，默认滚动条始终出现，初始化列表组件

        contentPane.add(scrollPane, BorderLayout.CENTER);

        setContentPane(contentPane);
        setTitle("育才中学初一.4班期末考试成绩表");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(Location_x, Location_y);
        setSize(DEFAULE_WIDTH, DEFAULE_HEIGH);
    }

    Color foreground;

    Color background;

    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (row == 0 && column == 1) {

            foreground = Color.red;

            background = Color.green;

        } else {

            foreground = Color.BLACK;

            background = Color.WHITE;

        }

        renderer.setForeground(foreground);

        renderer.setBackground(background);

        return renderer;
    }
}
