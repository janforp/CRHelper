package com.janita.plugin.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * CrQuestionState
 *
 * @author zhucj
 * @since 20220324
 */
@AllArgsConstructor
public enum CrQuestionState {

    UNSOLVED("未解决"),

    SOLVED("已解决"),

    REJECT("不予解决"),

    DUPLICATE("重复问题"),

    CLOSED("已关闭"),
    ;

    @Getter
    private final String desc;

    public static CrQuestionState getByDesc(String desc) {
        for (CrQuestionState state : CrQuestionState.values()) {
            if (state.getDesc().equals(desc)) {
                return state;
            }
        }
        return UNSOLVED;
    }
}
