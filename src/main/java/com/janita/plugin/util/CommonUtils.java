package com.janita.plugin.util;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.vcs.log.VcsUser;
import com.janita.plugin.common.domain.Pair;
import git4idea.GitUserRegistry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * CommonUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class CommonUtils {

    private static Set<Repository> relatedRepositorySet(AnActionEvent e, Project project) {
        VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(project);
        ProjectLevelVcsManager projectLevelVcsManager = ProjectLevelVcsManager.getInstance(project);
        Set<Repository> relatedRepositorySet = new HashSet<>();
        VirtualFile[] virtualFiles = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE_ARRAY);
        for (VirtualFile virtualFile : virtualFiles) {
            VcsRoot vcsRoot = projectLevelVcsManager.getVcsRootObjectFor(virtualFile);
            if (vcsRoot == null) {
                continue;
            }
            Repository repository = vcsRepositoryManager.getRepositoryForRootQuick(vcsRoot.getPath());
            relatedRepositorySet.add(repository);
        }
        return relatedRepositorySet;
    }

    public static Pair<String, String> getProjectNameAndBranchName(AnActionEvent e, Project project) {
        Set<Repository> repositorySet = relatedRepositorySet(e, project);
        if (repositorySet.size() == 0) {
            return Pair.of(null, null);
        }
        Repository repository = new ArrayList<>(repositorySet).get(0);
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

}