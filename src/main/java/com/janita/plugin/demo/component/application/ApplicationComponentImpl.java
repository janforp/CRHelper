package com.janita.plugin.demo.component.application;

import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

/**
 * CrDataFetchWayComponent
 *
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