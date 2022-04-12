package com.janita.plugin.camel;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorModificationUtil;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * 驼峰转换
 *
 * @author zhucj
 * @since 20220324
 */
public class ToggleCamelCaseAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getData(PlatformDataKeys.PROJECT);
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        Caret caret = e.getData(PlatformDataKeys.CARET);

        String selectedText = caret.getEditor().getSelectionModel().getSelectedText();
        if (StringUtils.isEmpty(selectedText)) {
            editor.getSelectionModel().selectWordAtCaret(true);
            return;
        }
        String newText;
        if (CamelUtils.isAllLowerCase(selectedText) && selectedText.contains(CamelUtils.UNDER_LINE)) {
            //如果全小写字母，包含下划线，则转换全大写，snake_case ----> SNAKE_CASE
            newText = selectedText.toUpperCase();
        } else if (CamelUtils.isAllUpperCase(selectedText) && selectedText.contains(CamelUtils.UNDER_LINE)) {
            //如果全大写字母，包含下划线，则转换全大写，SNAKE_CASE ----> SnakeCase
            newText = CamelUtils.toCamelCase(selectedText.toLowerCase());
        } else if (!CamelUtils.isAllUpperCase(selectedText)
                && CamelUtils.isFirstUpper(selectedText)
                && !selectedText.contains(CamelUtils.UNDER_LINE)) {
            //如果有大写小写，首字母大写，不包含下划线，则SnakeCase ----> snakeCase
            newText = selectedText.substring(0, 1).toLowerCase() + selectedText.substring(1);
        } else {
            //shaneCase ----> snake_case
            newText = CamelUtils.toSnakeCase(selectedText);
        }
        ApplicationManager.getApplication().runWriteAction(
                () -> CommandProcessor.getInstance().executeCommand(project,
                        () -> {
                            int start = editor.getSelectionModel().getSelectionStart();
                            EditorModificationUtil.insertStringAtCaret(editor, newText);
                            editor.getSelectionModel().setSelection(start, start + newText.length());
                        }, "CamelCase", ActionGroup.EMPTY_GROUP));
    }
}
