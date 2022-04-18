package com.janita.plugin.cr.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.janita.plugin.cr.domain.CrProjectInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * CrProjectInfoPersistent
 *
 * @author zhucj
 * @since 20220324
 */
@State(name = "CrProjectInfoPersistent", storages = { @Storage(value = "com.janita.CrProjectInfoPersistent.xml") })
public class CrProjectInfoPersistent implements PersistentStateComponent<List<CrProjectInfo>> {

    private List<CrProjectInfo> projectInfoList;

    public static CrProjectInfoPersistent getInstance() {
        return ServiceManager.getService(CrProjectInfoPersistent.class);
    }

    @Override
    public @Nullable
    List<CrProjectInfo> getState() {
        return projectInfoList;
    }

    @Override
    public void loadState(@NotNull List<CrProjectInfo> state) {
        this.projectInfoList = state;
    }
}