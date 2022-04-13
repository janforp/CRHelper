package com.janita.plugin.cr.domain;

import java.util.Date;

/**
 * CrQuestion
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestion {

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 问题类型
     */
    private String type;

    /**
     * 起始行
     */
    private Integer lineFrom;

    /**
     * 结束行
     */
    private Integer lineTo;

    /**
     * 类名称
     */
    private String className;

    /**
     * 问题代码
     */
    private String questionCode;

    /**
     * 建议写法
     */
    private String betterCode;

    /**
     * 描述
     */
    private String desc;

    /**
     * 提出问题的人
     */
    private String fromAccount;

    /**
     * 接收问题的人
     */
    private String toAccount;

    /**
     * git分支名称
     */
    private String gitBranchName;

    /**
     * 解决版本
     */
    private String solveGitBranchName;

    /**
     * 是否已经解决
     */
    private Boolean solve;

    /**
     * 提问时间
     */
    private Date createTime;

    /**
     * 解决时间
     */
    private Date solveTime;

    public String getType() {
        return type;
    }

    public Integer getLineFrom() {
        return lineFrom;
    }

    public Integer getLineTo() {
        return lineTo;
    }

    public String getClassName() {
        return className;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setSolveGitBranchName(String solveGitBranchName) {
        this.solveGitBranchName = solveGitBranchName;
    }

    public void setSolve(Boolean solve) {
        this.solve = solve;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setSolveTime(Date solveTime) {
        this.solveTime = solveTime;
    }

    public String getBetterCode() {
        return betterCode;
    }

    public String getDesc() {
        return desc;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getSolveGitBranchName() {
        return solveGitBranchName;
    }

    public Boolean getSolve() {
        return solve;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getSolveTime() {
        return solveTime;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLineFrom(Integer lineFrom) {
        this.lineFrom = lineFrom;
    }

    public void setLineTo(Integer lineTo) {
        this.lineTo = lineTo;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public void setBetterCode(String betterCode) {
        this.betterCode = betterCode;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getGitBranchName() {
        return gitBranchName;
    }

    public void setGitBranchName(String gitBranchName) {
        this.gitBranchName = gitBranchName;
    }

    @Override
    public String toString() {
        return "CrQuestion{" +
                "projectName='" + projectName + '\'' +
                ", type='" + type + '\'' +
                ", lineFrom=" + lineFrom +
                ", lineTo=" + lineTo +
                ", className='" + className + '\'' +
                ", questionCode='" + questionCode + '\'' +
                ", betterCode='" + betterCode + '\'' +
                ", desc='" + desc + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", gitBranchName='" + gitBranchName + '\'' +
                ", solveGitBranchName='" + solveGitBranchName + '\'' +
                ", solve=" + solve +
                ", createTime=" + createTime +
                ", solveTime=" + solveTime +
                '}';
    }
}