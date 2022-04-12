package com.janita.plugin.markbook;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.janita.plugin.markbook.data.DataCenter;
import com.janita.plugin.markbook.dialog.NoteDialog;

/**
 * github.com/jogeen/MarkBook
 *
 * @author zhucj
 * @since 20220324
 */
public class MarkBookAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        SelectionModel selectionModel = editor.getSelectionModel();

        // 用户选择的文本
        DataCenter.SELECT_TEXT = selectionModel.getSelectedText();
        // 当前文件的名称
        DataCenter.FILE_NAME = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();

        NoteDialog dialog = new NoteDialog(project);
        dialog.open();
    }
}
