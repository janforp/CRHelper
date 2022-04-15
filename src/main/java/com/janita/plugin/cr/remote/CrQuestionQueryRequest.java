package com.janita.plugin.cr.remote;

import java.util.Set;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestionQueryRequest {

    /**
     * 状态
     */
    private final Set<String> stateSet;

    /**
     * 当前 project 下的所有仓库名称
     */
    private final Set<String> projectNameSet;

    public CrQuestionQueryRequest(Set<String> stateSet, Set<String> projectNameSet) {
        this.stateSet = stateSet;
        this.projectNameSet = projectNameSet;
    }

    public Set<String>  getState() {
        return stateSet;
    }

    public Set<String> getProjectNameSet() {
        return projectNameSet;
    }

    @Override
    public String toString() {
        return "CrQuestionQueryRequest{" +
                "stateSet=" + stateSet +
                ", projectNameSet=" + projectNameSet +
                '}';
    }
}