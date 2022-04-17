package com.janita.plugin.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.janita.plugin.persistent.data.DataSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * TestPersistentStateComponent
 *
 * @author zhucj
 * @since 20220324
 */
@State(name = "TestPersistentStateComponent", storages = { @Storage(value = "TestPersistentStateComponent.xml") })
public class TestPersistentStateComponent implements PersistentStateComponent<DataSource> {

    private DataSource test;

    @Override
    public @Nullable
    DataSource getState() {
        return test;
    }

    @Override
    public void loadState(@NotNull DataSource state) {
        test = state;
    }
}
