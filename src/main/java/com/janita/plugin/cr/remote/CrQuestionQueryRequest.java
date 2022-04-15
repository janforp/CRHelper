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
     * 账户
     */
    private final String account;

    /**
     * 当前 project 下的所有仓库名称
     */
    private final Set<String> projectNameSet;

    public CrQuestionQueryRequest(String account, Set<String> projectNameSet) {
        this.account = account;
        this.projectNameSet = projectNameSet;
    }

    public String getAccount() {
        return account;
    }

    public Set<String> getProjectNameSet() {
        return projectNameSet;
    }
}
