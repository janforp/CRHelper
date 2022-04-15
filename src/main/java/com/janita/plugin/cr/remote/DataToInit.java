package com.janita.plugin.cr.remote;

import java.util.Arrays;
import java.util.List;

/**
 * DataToInit
 *
 * @author zhucj
 * @since 20220324
 */
public class DataToInit {

    /**
     * 问题类型
     */
    public static final List<String> QUESTION_TYPE_LIST = Arrays.asList("建议", "性能", "缺陷", "规范", "业务");

    /**
     * 账户
     */
    public static final List<String> ACCOUNT_LIST = Arrays.asList("王尚飞", "朱晨剑", "张丹", "杨艳斌");

    /**
     * 问题级别
     */
    public static final List<String> LEVEL_LIST = Arrays.asList("1", "2", "3", "4", "5");

    /**
     * 状态
     */
    public static final List<String> STATE_LIST = Arrays.asList("未解决", "已解决", "重复问题", "已关闭");
}