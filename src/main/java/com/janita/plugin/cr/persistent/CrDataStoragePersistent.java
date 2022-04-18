package com.janita.plugin.cr.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.janita.plugin.cr.domain.CrDataStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 用来存储保存cr数据的方式
 *
 * @author zhucj
 * @since 20220324
 */
@State(name = "com.janita.CrDataStoragePersistent", storages = { @Storage(value = "com.janita.plugin.CrDataStoragePersistent.xml") })
public class CrDataStoragePersistent implements PersistentStateComponent<CrDataStorage> {

    private CrDataStorage storageWay;

    public static CrDataStoragePersistent getInstance() {
        return ServiceManager.getService(CrDataStoragePersistent.class);
    }

    public static CrDataStorage getPersistentData() {
        return getInstance().getState();
    }

    @Override
    public @Nullable
    CrDataStorage getState() {
        return storageWay;
    }

    @Override
    public void loadState(@NotNull CrDataStorage state) {
        this.storageWay = state;
    }
}
