package com.janita.plugin.cr;

import com.intellij.dvcs.repo.Repository;
import com.intellij.dvcs.repo.VcsRepositoryManager;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import com.intellij.openapi.vcs.VcsRoot;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.changes.CommitExecutor;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PairConsumer;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * VersionCheckerHandlerFactory
 *
 * @author shouguouo
 * @since 2021-12-17 20:24:32
 */
public class VersionCheckerHandlerFactory extends CheckinHandlerFactory {

    private static final Logger LOGGER = Logger.getInstance(VersionCheckerHandlerFactory.class);

    private static final String TEXT = "Check Version Number";

    private static final String STATUS_LOCATION = "com.zitiger.plugin.xitk.VersionChecker";

    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        return new CheckinHandler() {

            @Override
            public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
                final JCheckBox checkVersion = new JCheckBox(TEXT);
                return new RefreshableOnComponent() {
                    @Override
                    public JComponent getComponent() {
                        final JPanel panel = new JPanel(new BorderLayout());
                        panel.add(checkVersion, BorderLayout.WEST);
                        return panel;
                    }

                    @Override
                    public void refresh() {
                    }

                    @Override
                    public void saveState() {
                        setStatus(checkVersion.isSelected());
                    }

                    @Override
                    public void restoreState() {
                        checkVersion.setSelected(getStatus());
                    }
                };
            }

            @Override
            public ReturnResult beforeCheckin(@Nullable CommitExecutor executor, PairConsumer<Object, Object> additionalDataConsumer) {
                if (getStatus()) {
                    VcsRepositoryManager vcsRepositoryManager = VcsRepositoryManager.getInstance(panel.getProject());
                    // 获取需要校验的仓库
                    Set<Repository> relatedRepositorySet = new HashSet<>();
                    ProjectLevelVcsManager vcsManager = ProjectLevelVcsManager.getInstance(panel.getProject());
                    for (VirtualFile virtualFile : panel.getVirtualFiles()) {
                        VcsRoot root = vcsManager.getVcsRootObjectFor(virtualFile);
                        if (root == null) {
                            continue;
                        }
                        if (root.getPath() == null) {
                            continue;
                        }
                        relatedRepositorySet.add(vcsRepositoryManager.getRepositoryForRootQuick(root.getPath()));
                    }
                    Set<Repository> unPass = new HashSet<>();
                    for (Repository repository : relatedRepositorySet) {
                        VirtualFile local = findPomFile(repository);
                        if (local == null) {
                            continue;
                        }
                        // 获取本地pom文件version版本
                        String versionFromPomFile;
                        try {
                            versionFromPomFile = getVersionFromPomFile(local);
                        } catch (Exception e) {
                            LOGGER.error(e);
                            continue;
                        }
                        String currentBranchName = repository.getCurrentBranchName();
                        if (StringUtils.equals(versionFromPomFile, currentBranchName)) {
                            continue;
                        }
                        unPass.add(repository);
                    }
                    if (unPass.isEmpty()) {
                        return super.beforeCheckin(executor, additionalDataConsumer);
                    }
                    Messages.showMessageDialog(panel.getProject(), "以下工程POM与GIT版本不匹配：\n" + unPass.stream().map(x -> x.getRoot().getName()).collect(Collectors.joining("\n")), "版本校验不通过", Messages.getErrorIcon());
                    return ReturnResult.CANCEL;
                }
                return super.beforeCheckin(executor, additionalDataConsumer);
            }

            private String getVersionFromPomFile(VirtualFile pomFile) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(pomFile.getInputStream());
                XPathFactory xPathFactory = XPathFactory.newInstance();
                XPath xPath = xPathFactory.newXPath();
                NodeList evaluate = (NodeList) xPath.evaluate("/project/properties/version.number", doc, XPathConstants.NODESET);
                if (evaluate.getLength() > 0) {
                    Node item = evaluate.item(0);
                    return item.getTextContent();
                }
                return "";
            }

            private VirtualFile findPomFile(Repository repository) {
                for (VirtualFile child : repository.getRoot().getChildren()) {
                    if (StringUtils.equalsIgnoreCase("pom.xml", child.getName())) {
                        return child;
                    }
                }
                return null;
            }

            private boolean getStatus() {
                return PropertiesComponent.getInstance(panel.getProject()).getBoolean(STATUS_LOCATION, Boolean.TRUE);
            }

            private void setStatus(boolean status) {
                PropertiesComponent.getInstance(panel.getProject()).setValue(STATUS_LOCATION, status, Boolean.TRUE);
            }
        };
    }
}
