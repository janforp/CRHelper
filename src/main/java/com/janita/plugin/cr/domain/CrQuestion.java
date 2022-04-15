package com.janita.plugin.cr.domain;

/**
 * @author zhucj
 * @since 20220415
 */
public class CrQuestion {

    /**
     * id
     */
    private Long id;

    /**
     * 该问题的级别,类似复杂程度,数字越大问题越复杂
     */
    private String level;

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
     * 状态
     * 未解决,已解决,重复问题,已关闭
     */
    private String state;

    /**
     * 提问时间
     */
    private String createTime;

    /**
     * 解决时间
     */
    private String solveTime;

    public Long getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public String getProjectName() {
        return projectName;
    }

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

    public String getBetterCode() {
        return betterCode;
    }

    public String getDesc() {
        return desc;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getGitBranchName() {
        return gitBranchName;
    }

    public String getSolveGitBranchName() {
        return solveGitBranchName;
    }

    public String getState() {
        return state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getSolveTime() {
        return solveTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public void setGitBranchName(String gitBranchName) {
        this.gitBranchName = gitBranchName;
    }

    public void setSolveGitBranchName(String solveGitBranchName) {
        this.solveGitBranchName = solveGitBranchName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setSolveTime(String solveTime) {
        this.solveTime = solveTime;
    }

    @Override
    public String toString() {
        return "CrQuestion{" +
                "id=" + id +
                ", level='" + level + '\'' +
                ", projectName='" + projectName + '\'' +
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
                ", state='" + state + '\'' +
                ", createTime='" + createTime + '\'' +
                ", solveTime='" + solveTime + '\'' +
                '}';
    }
}