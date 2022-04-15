package com.janita.plugin.util;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationDisplayType;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcs.log.VcsUser;
import com.janita.plugin.common.domain.Pair;
import git4idea.GitUserRegistry;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * CommonUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class CommonUtils {

    private static Repository getRepositoryOfCurrentFile(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(project);
        ProjectLevelVcsManager projectLevelVcsManager = ProjectLevelVcsManager.getInstance(project);
        VirtualFile file = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        VcsRoot vcsRoot = projectLevelVcsManager.getVcsRootObjectFor(file);
        if (vcsRoot == null) {
            return null;
        }
        return vcsRepositoryManager.getRepositoryForRootQuick(vcsRoot.getPath());
    }

    private static Set<Repository> getAllRepositoryInProject(Project project) {
        VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(project);
        Collection<Repository> repositories = vcsRepositoryManager.getRepositories();
        return new HashSet<>(repositories);
    }

    public static Set<String> getAllProjectName(Project project) {
        Set<Repository> repositorySet = getAllRepositoryInProject(project);
        Set<String> projectNameSet = new HashSet<>();
        for (Repository repository : repositorySet) {
            projectNameSet.add(repository.getRoot().getName());
        }
        return projectNameSet;
    }

    public static Pair<String, String> getProjectNameAndBranchName(AnActionEvent e) {
        Repository repository = getRepositoryOfCurrentFile(e);
        if (repository == null) {
            return Pair.of("", "");
        }
        String projectName = repository.getRoot().getName();
        String currentBranchName = repository.getCurrentBranchName();
        return Pair.of(projectName, currentBranchName);
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

    public static VcsUser getGitUser(AnActionEvent e) {
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        GitUserRegistry instance = GitUserRegistry.getInstance(project);
        return instance.getOrReadUser(project.getBaseDir());
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
        NotificationGroup notificationGroup = new NotificationGroup("Code review", NotificationDisplayType.BALLOON, true);
        Notification notification = notificationGroup.createNotification(content, messageType);
        notification.setTitle("Code review");
        Notifications.Bus.notify(notification);
    }
}