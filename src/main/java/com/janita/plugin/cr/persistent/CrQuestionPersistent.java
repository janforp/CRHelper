package com.janita.plugin.cr.persistent;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.janita.plugin.cr.domain.CrQuestion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * CrQuestionPersistent
 *
 * @author zhucj
 * @since 20220324
 */
@State(name = "CrQuestionPersistent", storages = { @Storage(value = "CrQuestionPersistent.xml") })
public class CrQuestionPersistent implements PersistentStateComponent<List<CrQuestion>> {

    private List<CrQuestion> questionList;

    public static CrQuestionPersistent getInstance() {
        return ServiceManager.getService(CrQuestionPersistent.class);
    }

    @Override
    public @Nullable
    List<CrQuestion> getState() {
        return questionList;
    }

    @Override
    public void loadState(@NotNull List<CrQuestion> questionList) {
        this.questionList = questionList;
    }
}
