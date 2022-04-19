package com.janita.plugin.common.util;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.domain.SelectTextOffLineHolder;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.net.URL;

/**
 * CommonUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class CommonUtils {

    public static SelectTextOffLineHolder getSelectTextOffLineHolder(Editor editor) {
        Document document = editor.getDocument();
        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        int leadSelectionOffset = selectionModel.getLeadSelectionOffset();
        int documentStartLine = document.getLineNumber(leadSelectionOffset);
        VisualPosition startPosition = selectionModel.getSelectionStartPosition();
        if (startPosition == null) {
            startPosition = new VisualPosition(0, 0);
        }
        VisualPosition endPosition = selectionModel.getSelectionEndPosition();
        if (endPosition == null) {
            endPosition = new VisualPosition(0, 0);
        }
        SelectTextOffLineHolder holder = SelectTextOffLineHolder.builder()
                .selectedText(selectedText)
                .documentStartLine(documentStartLine)
                .documentEndLine(0)
                .leadSelectionOffset(leadSelectionOffset)
                .selectionStartPositionLine(startPosition.getLine())
                .selectionStartPositionColumn(startPosition.getColumn())
                .selectionEndPositionLine(endPosition.getLine())
                .selectionEndPositionColumn(endPosition.getColumn())
                .fileFullName(null)
                .build();
        int documentEndLine = holder.getDocumentEndLine();
        holder.setDocumentEndLine(documentEndLine);
        return holder;
    }

    public static Pair<Integer, Integer> getStartAndEndLine(Editor editor) {
        SelectionModel selectionModel = editor.getSelectionModel();
        VisualPosition startPosition = selectionModel.getSelectionStartPosition();
        VisualPosition endPosition = selectionModel.getSelectionEndPosition();
        int startLine = 0, endLine = 0;
        if (startPosition != null) {
            startLine = startPosition.getLine();
        }
        if (endPosition != null) {
            endLine = endPosition.getLine();
        }
        return Pair.of(startLine, endLine);
    }

    public static String getSelectedText(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        SelectionModel selectionModel = editor.getSelectionModel();
        // 用户选择的文本
        return selectionModel.getSelectedText();
    }

    public static String getClassName(AnActionEvent e) {
        // 当前文件的名称
        return e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
    }

    public static void setToClipboard(String text) {
        // 拿到剪贴板
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(text);
        clipboard.setContents(stringSelection, null);
    }

    public static JMenuItem buildJMenuItem(String title, String icon, ActionListener listener) {
        JMenuItem item = new JMenuItem();
        item.setText(title);
        ImageIcon imageIcon = getImageIcon(icon, 20, 20);
        item.setIcon(imageIcon);
        item.addActionListener(listener);
        return item;
    }

    public static void clearDefaultTableModel(DefaultTableModel model) {
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    public static JPopupMenu buildJPopupMenu(JMenuItem... items) {
        JPopupMenu menu = new JPopupMenu();
        if (items == null || items.length == 0) {
            return menu;
        }
        for (JMenuItem item : items) {
            menu.add(item);
        }
        return menu;
    }

    public static ImageIcon getImageIcon(String path, int width, int height) {
        URL resource = CommonUtils.class.getResource(path);
        if (resource == null) {
            return new ImageIcon();
        }
        if (width == 0 || height == 0) {
            return new ImageIcon(resource);
        }
        ImageIcon icon = new ImageIcon(resource);
        icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        return icon;
    }

    public static boolean hasSelectAnyText(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        return editor.getSelectionModel().hasSelection();
    }

    public static void showNotification(String content, MessageType messageType) {
        NotificationGroup notificationGroup = new NotificationGroup("Code Review", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(content, messageType);
        notification.setTitle("Code review");
        Notifications.Bus.notify(notification);
    }

    /**
     * 根据文件名称，打开该文件
     */
    public static void openFileAndLocationToText(Project project, String fileName, Integer lineStart, String locationText) {
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        if (psiFiles.length == 0) {
            return;
        }
        PsiFile psiFile = psiFiles[0];
        // 光标移动到指定位置
        VirtualFile virtualFile = psiFile.getVirtualFile();

        PsiDocumentManager documentManager = PsiDocumentManager.getInstance(project);
        Document document = documentManager.getDocument(psiFile);
        OpenFileDescriptor descriptor;
        if (document == null) {
            descriptor = new OpenFileDescriptor(project, virtualFile, lineStart, 0);
            descriptor.navigate(true);
        } else {
            String text = document.getText();
            int indexOf = text.indexOf(locationText);
            descriptor = new OpenFileDescriptor(project, virtualFile, indexOf);
        }
        descriptor.navigate(true);
    }

    public static Project getProject() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        Project activeProject = null;
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                activeProject = project;
            }
        }
        return activeProject;
    }
}