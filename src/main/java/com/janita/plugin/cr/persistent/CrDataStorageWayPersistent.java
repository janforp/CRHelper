package com.janita.plugin.cr.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.janita.plugin.cr.domain.CrDataStorageWay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 用来存储保存cr数据的方式
 *
 * @author zhucj
 * @since 20220324
 */
@State(name = "CrDataStorageWayPersistent", storages = { @Storage(value = "CrDataStorageWayPersistent.xml") })
public class CrDataStorageWayPersistent implements PersistentStateComponent<CrDataStorageWay> {

    private CrDataStorageWay storageWay;

    @Override
    public @Nullable
    CrDataStorageWay getState() {
        return storageWay;
    }

    @Override
    public void loadState(@NotNull CrDataStorageWay state) {
        this.storageWay = state;
    }
}
