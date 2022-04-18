package com.janita.plugin.demo.component.module;

import com.intellij.openapi.module.ModuleComponent;

/**
 * ModuleComponentImpl
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
