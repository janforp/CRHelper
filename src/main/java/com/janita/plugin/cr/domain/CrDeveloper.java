package com.janita.plugin.cr.domain;

/**
 * 开发者
 *
 * @author zhucj
 * @since 20220324
 */
public class CrDeveloper {

    /**
     * git名称如zhucj
     */
    private final String gitUserName;

    /**
     * 邮箱
     */
    private final String email;

    /**
     * 真实姓名如朱晨剑
     */
    private final String realName;

    public CrDeveloper(String gitUserName, String email, String realName) {
        this.gitUserName = gitUserName;
        this.email = email;
        this.realName = realName;
    }

    public String getGitUserName() {
        return gitUserName;
    }

    public String getEmail() {
        return email;
    }

    public String getRealName() {
        return realName;
    }
}
