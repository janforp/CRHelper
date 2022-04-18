package com.janita.plugin.demo;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.options.UnnamedConfigurable;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.VcsException;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.changes.CommitExecutor;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PairConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * TestCheckinHandlerFactory
 *
 * @author zhucj
 * @since 20220324
 */
public class TestCheckinHandlerFactory extends CheckinHandlerFactory {

    @Override
    public @NotNull
    CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        String commitMessage = panel.getCommitMessage();
        System.out.println("提交代码的时候写的commitMessage：" + commitMessage);
        Collection<VirtualFile> virtualFiles = panel.getVirtualFiles();
        System.out.println("本次提交的文件有：");
        for (VirtualFile virtualFile : virtualFiles) {
            System.out.println(virtualFile.getName());
        }
        return new CheckinHandler() {
            @Override
            public @Nullable
            RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
                return super.getBeforeCheckinConfigurationPanel();
            }

            @Override
            @SuppressWarnings("all")
            public @Nullable
            UnnamedConfigurable getBeforeCheckinSettings() {
                return super.getBeforeCheckinSettings();
            }

            @Override
            public @Nullable
            RefreshableOnComponent getAfterCheckinConfigurationPanel(Disposable parentDisposable) {
                return super.getAfterCheckinConfigurationPanel(parentDisposable);
            }

            @Override
            public ReturnResult beforeCheckin(@Nullable CommitExecutor executor, PairConsumer<Object, Object> additionalDataConsumer) {
                return super.beforeCheckin(executor, additionalDataConsumer);
            }

            @Override
            public ReturnResult beforeCheckin() {
                return super.beforeCheckin();
            }

            @Override
            public void checkinSuccessful() {
                super.checkinSuccessful();
            }

            @Override
            public void checkinFailed(List<VcsException> exception) {
                super.checkinFailed(exception);
            }

            @Override
            public void includedChangesChanged() {
                super.includedChangesChanged();
            }

            @Override
            public boolean acceptExecutor(CommitExecutor executor) {
                return super.acceptExecutor(executor);
            }
        };
    }
}
