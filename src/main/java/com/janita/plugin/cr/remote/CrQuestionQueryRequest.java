package com.janita.plugin.cr.remote;

import java.util.Set;

/**
 * CrQuestionQueryRequest
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestionQueryRequest {

    /**
     * 状态
     */
    private final String state;

    /**
     * 当前 project 下的所有仓库名称
     */
    private final Set<String> projectNameSet;

    public CrQuestionQueryRequest(String state, Set<String> projectNameSet) {
        this.state = state;
        this.projectNameSet = projectNameSet;
    }

    public String getState() {
        return state;
    }

    public Set<String> getProjectNameSet() {
        return projectNameSet;
    }
}