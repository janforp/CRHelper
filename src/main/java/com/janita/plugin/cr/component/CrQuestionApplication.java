package com.janita.plugin.cr.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;

/**
 * CrQuestionApplication
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionApplication implements ApplicationComponent {

    @Override
    public void disposeComponent() {
        SqliteDatabaseServiceImpl.getInstance().closeResource();
        ApplicationComponent.super.disposeComponent();
    }
}
