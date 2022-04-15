package com.janita.plugin.cr.domain;

import java.util.Set;

/**
 * ProjectInfo
 *
 * @author zhucj
 * @since 20220324
 */
public class CrProjectInfo {

    /**
     * 项目名称如 socinscore
     */
    private final String projectName;

    /**
     * 开发人员
     */
    private final Set<CrDeveloper> developers;

    public CrProjectInfo(String projectName, Set<CrDeveloper> developers) {
        this.projectName = projectName;
        this.developers = developers;
    }

    public String getProjectName() {
        return projectName;
    }

    public Set<CrDeveloper> getDevelopers() {
        return developers;
    }
}