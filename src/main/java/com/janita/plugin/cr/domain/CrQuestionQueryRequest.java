package com.janita.plugin.cr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Set;

/**
 * @author zhucj
 * @since 20220415
 */
@Data
@ToString
@AllArgsConstructor
public class CrQuestionQueryRequest {

    /**
     * 状态
     */
    private final Set<String> stateSet;

    /**
     * 当前 project 下的所有仓库名称
     */
    private final Set<String> projectNameSet;
}