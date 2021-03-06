package com.janita.plugin.demo.mybatislog2sql;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.janita.plugin.demo.mybatislog2sql.other.LogParse;
import com.janita.plugin.demo.mybatislog2sql.other.SqlFormatter;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 * mybatis日志转换为可执行大sql
 *
 * Preparing: select * from student where id = ? and name = ?
 * Parameters: 1(String), 张三(String)
 *
 * 复制上面的日志
 * 粘贴结果：
 *
 * select
 * *
 * from
 * student
 * where
 * id = '1'
 * and name = '张三'
 *
 * @author zhucj
 * @since 202003
 */
public class MybatisLog2SqlAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        String log = editor.getSelectionModel().getSelectedText();
        if (StringUtils.isEmpty(log)) {
            return;
        }
        String sql = LogParse.toSql(log);
        if (StringUtils.isEmpty(sql)) {
            return;
        }

        // 拿到剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        // 格式化之后的sql
        String formatSql = SqlFormatter.format(sql);
        StringSelection stringSelection = new StringSelection(formatSql);
        clipboard.setContents(stringSelection, null);
    }
}
