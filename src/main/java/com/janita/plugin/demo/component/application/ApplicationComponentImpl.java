package com.janita.plugin.demo.component.application;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * CrDataFetchWayComponent
 *
 * @author zhucj
 * @since 20220324
 */
public class ApplicationComponentImpl implements ApplicationComponent {

    @Override
    public void initComponent() {
        ApplicationComponent.super.initComponent();
        System.out.println("ModuleComponentImpl-initComponent");
    }

    @Override
    public @NotNull String getComponentName() {
        return "Code review fetch data way";
    }
}