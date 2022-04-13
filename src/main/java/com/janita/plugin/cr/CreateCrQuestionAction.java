package com.janita.plugin.cr;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vfs.VirtualFile;
import com.janita.plugin.cr.dialog.QuestionDialog;
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
public class CreateCrQuestionAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        SelectionModel selectionModel = editor.getSelectionModel();

        // 用户选择的文本
        String questionCode = selectionModel.getSelectedText();
        // 当前文件的名称
        String className = e.getRequiredData(CommonDataKeys.PSI_FILE).getViewProvider().getVirtualFile().getName();
        System.out.println(questionCode);
        System.out.println(className);
        CrQuestion question = new CrQuestion();
        question.setType(null);
        question.setLineFrom(null);
        question.setLineTo(null);
        question.setClassName(className);
        question.setQuestionCode(questionCode);
        question.setBetterCode(null);
        question.setDesc(null);
        question.setFromAccount(null);
        question.setToAccount(null);
        question.setGitBranchName(getCurrentBranchName(e, project));
        QuestionDialog dialog = new QuestionDialog(project);
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

    private String getCurrentBranchName(AnActionEvent e, Project project) {
        Set<Repository> repositorySet = relatedRepositorySet(e, project);
        if (repositorySet.size() == 0) {
            return null;
        }
        return new ArrayList<>(repositorySet).get(0).getCurrentBranchName();
    }
}