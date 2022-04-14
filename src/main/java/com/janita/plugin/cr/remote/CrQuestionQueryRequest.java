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
    private String account;

    /**
     * 当前 project 下的所有仓库名称
     */
    private Set<String> projectNameSet;

    public CrQuestionQueryRequest(String account, Set<String> projectNameSet) {
        this.account = account;
        this.projectNameSet = projectNameSet;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
