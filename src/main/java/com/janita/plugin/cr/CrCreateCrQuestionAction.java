package com.janita.plugin.cr;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.editor.VisualPosition;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vfs.VirtualFile;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.cr.dialog.CrCreateQuestionDialog;
import com.janita.plugin.cr.domain.CrQuestion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * CreateCrQuestionAction
 *
 * @author zhucj
 * @since 20220324
 */
public class CrCreateCrQuestionAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
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

        // 用户选择的文本
        String questionCode = selectionModel.getSelectedText();
        // 当前文件的名称
        String className = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        Pair<String, String> vcsPair = getCurrentBranchName(e, project);
        CrQuestion question = new CrQuestion();
        question.setProjectName(vcsPair.getLeft());
        question.setType(null);
        question.setLineFrom(startLine);
        question.setLineTo(endLine);
        question.setClassName(className);
        question.setQuestionCode(questionCode);
        question.setBetterCode(null);
        question.setDesc(null);
        question.setFromAccount(null);
        question.setToAccount(null);
        question.setGitBranchName(vcsPair.getRight());
        CrCreateQuestionDialog dialog = new CrCreateQuestionDialog(project);
        dialog.open(question);
    }

    private Set<Repository> relatedRepositorySet(AnActionEvent e, Project project) {
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

    private Pair<String, String> getCurrentBranchName(AnActionEvent e, Project project) {
        Set<Repository> repositorySet = relatedRepositorySet(e, project);
        if (repositorySet.size() == 0) {
            return Pair.of(null, null);
        }
        Repository repository = new ArrayList<>(repositorySet).get(0);
        String projectName = repository.getRoot().getName();
        String currentBranchName = repository.getCurrentBranchName();
        return Pair.of(projectName, currentBranchName);
    }
}