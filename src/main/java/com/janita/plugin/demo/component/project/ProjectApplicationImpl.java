package com.janita.plugin.demo.component.project;

import com.intellij.openapi.components.ProjectComponent;

/**
 * ProjectApplicationImpl
 * *     <application-components>
 * *         <component>
 * *             <implementation-class>com.janita.plugin.demo.component.application.ApplicationComponentImpl</implementation-class>
 * *         </component>
 * *     </application-components>
 * *
 * *     <project-components>
 * *         <component>
 * *             <implementation-class>com.janita.plugin.demo.component.project.ProjectApplicationImpl</implementation-class>
 * *         </component>
 * *     </project-components>
 * *
 * *     <module-components>
 * *         <component>
 * *             <implementation-class>com.janita.plugin.demo.component.module.ModuleComponentImpl</implementation-class>
 * *         </component>
 * *     </module-components>
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
