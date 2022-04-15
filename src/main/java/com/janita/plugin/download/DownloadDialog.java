package com.janita.plugin.download;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DownloadDialog extends DialogWrapper implements ActionListener {

    private JTextField downloadUrlField;

    private JTextField fileDirField;

    private JTextField threadNumField;

    private final JFileChooser chooser = new JFileChooser();

    public DownloadDialog() {
        super(true); // use current window as parent
        init();
        setTitle("File Fast Download");
        setSize(300, 300);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // 设定只能选择到文件夹
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        Box verticalBox = Box.createVerticalBox();

        // 下载文件地址部分
        Box urlBox = createUrlBox();
        verticalBox.add(urlBox);

        // 中间间隔距离
        verticalBox.add(Box.createVerticalStrut/*创建垂直支柱*/(10));

        // 下载文件存储目录部分
        Box fileDirJPanel = createFileDirJPanel();
        verticalBox.add(fileDirJPanel);

        // 中间间隔距离
        verticalBox.add(Box.createVerticalStrut(10));

        // 下载线程数量部分
        Box threadNumJPanel = createThreadNumJPanel();
        verticalBox.add(threadNumJPanel);
        return verticalBox;
    }

    private Box createUrlBox() {
        Box horizontalBox = Box.createHorizontalBox();
        JLabel label = new JLabel("文件下载地址:");
        horizontalBox.add(label);
        horizontalBox.add(Box.createHorizontalStrut/*创建水平支柱*/(10));
        downloadUrlField = new JTextField(20);
        horizontalBox.add(downloadUrlField);
        return horizontalBox;
    }

    private Box createFileDirJPanel() {
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(new JLabel("文件保存目录:"));
        horizontalBox.add(Box.createHorizontalStrut(10));

        fileDirField = new JTextField(15);
        horizontalBox.add(fileDirField);
        horizontalBox.add(Box.createHorizontalStrut(10));

        JButton button = new JButton("浏览");
        horizontalBox.add(button);
        button.addActionListener(this);
        return horizontalBox;
    }

    private Box createThreadNumJPanel() {
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(new JLabel("下载线程数:"));
        horizontalBox.add(Box.createHorizontalStrut(22));

        threadNumField = new JTextField("10", 5);
        horizontalBox.add(threadNumField);
        return horizontalBox;
    }

    /**
     * 点击浏览按钮的时候执行的操作
     *
     * @param e 事件
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int state = chooser.showOpenDialog(null);// 此句是打开文件选择器界面的触发语句
        if (state == 1) {
            return;
        }
        File f = chooser.getSelectedFile();// f为选择到的目录
        fileDirField.setText(f.getAbsolutePath());
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (StringUtils.isBlank(downloadUrlField.getText())) {
            return new ValidationInfo("文件下载地址必填");
        }
        if (StringUtils.isBlank(fileDirField.getText())) {
            return new ValidationInfo("文件保存目录必填");
        }
        if (StringUtils.isBlank(threadNumField.getText())) {
            return new ValidationInfo("下载线程数必填");
        }
        return null;
    }

    public DialogInputHolder getDialogInputHolder() {
        return new DialogInputHolder(downloadUrlField.getText(), fileDirField.getText(), Integer.valueOf(threadNumField.getText()));
    }

    public static class DialogInputHolder {

        private final String downloadUrl;

        private final String dirUrl;

        private final Integer threadNum;

        public DialogInputHolder(String downloadUrl, String dirUrl, Integer threadNum) {
            this.downloadUrl = downloadUrl;
            this.dirUrl = dirUrl;
            this.threadNum = threadNum;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public String getDirUrl() {
            return dirUrl;
        }

        public int getThreadNum() {
            return threadNum;
        }
    }
}
