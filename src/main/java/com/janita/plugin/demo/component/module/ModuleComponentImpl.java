package com.janita.plugin.demo.component.module;

import com.intellij.openapi.module.ModuleComponent;

/**
 * ModuleComponentImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class ModuleComponentImpl implements ModuleComponent {

    @Override
    public void moduleAdded() {
        ModuleComponent.super.moduleAdded();
        System.out.println("ModuleComponentImpl-moduleAdded");
    }

    @Override
    public void projectOpened() {
        ModuleComponent.super.projectOpened();
        System.out.println("ModuleComponentImpl-projectOpened");
    }
}
