package com.janita.plugin.cr.domain;

/**
 * CrQuestion
 *
 * @author zhucj
 * @since 20220324
 */
public class CrQuestion {

    /**
     * 问题类型
     */
    private QuestionType type;

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
    private String wrongCode;

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
}