package com.janita.plugin.demo.component.project;

import com.intellij.openapi.components.ProjectComponent;

/**
 * ProjectApplicationImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class ProjectApplicationImpl implements ProjectComponent {

    @Override
    public void projectOpened() {
        ProjectComponent.super.projectOpened();
        System.out.println("ProjectApplicationImpl-projectOpened");
    }
}
