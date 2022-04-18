package com.janita.plugin.demo;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.changes.CommitExecutor;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
import com.intellij.util.PairConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * VersionCheckerHandlerFactory
 *
 * 需要在 plugin.xml 中配置才生效!!!!!!!!
 * <extensions defaultExtensionNs="com.intellij">
 * ******* <checkinHandlerFactory implementation="com.janita.plugin.demo.VersionCheckerHandlerFactory"/>
 * </extensions>
 *
 * @author zhucj
 * @since 20220324
 */
public class VersionCheckerHandlerFactory extends CheckinHandlerFactory {

    private static final String TEXT = "Check Version Number";

    private static final String STATUS_LOCATION = "com.zitiger.plugin.xitk.VersionChecker";

    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
        return new CheckinHandler() {

            @Override
            public RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
                System.out.println("CheckinHandler.getBeforeCheckinConfigurationPanel - 在打开提交代码对话框之前，我们可以在这里做一些其他事情！！！");
                final JCheckBox checkVersion = new JCheckBox(TEXT);
                return new RefreshableOnComponent() {
                    @Override
                    public JComponent getComponent() {
                        final JPanel panel = new JPanel(new BorderLayout());
                        // 放在右侧
                        panel.add(checkVersion, BorderLayout.WEST);
                        return panel;
                    }

                    @Override
                    public void refresh() {
                    }

                    @Override
                    public void saveState() {
                        // PropertiesComponent 可以用于存储一些简单的数据
                        setStatus(checkVersion.isSelected());
                    }

                    @Override
                    public void restoreState() {
                        // PropertiesComponent 可以用于存储一些简单的数据
                        checkVersion.setSelected(getStatus());
                    }
                };
            }

            @Override
            public ReturnResult beforeCheckin(@Nullable CommitExecutor executor, PairConsumer<Object, Object> additionalDataConsumer) {
                System.out.println("CheckinHandler.beforeCheckin - 在提交代码之前，我们可以在这里做一些其他事情！！！");
                return super.beforeCheckin(executor, additionalDataConsumer);
            }

            @Override
            public void checkinSuccessful() {
                System.out.println("CheckinHandler.checkinSuccessful - 在提交代码成功之后，我们可以在这里做一些其他事情！！！");
                super.checkinSuccessful();
            }

            private boolean getStatus() {
                return PropertiesComponent.getInstance(panel.getProject()).getBoolean(STATUS_LOCATION, Boolean.TRUE);
            }

            private void setStatus(boolean status) {
                // PropertiesComponent 可以用于存储一些简单的数据
                PropertiesComponent.getInstance(panel.getProject()).setValue(STATUS_LOCATION, status, Boolean.TRUE);
            }
        };
    }
}
