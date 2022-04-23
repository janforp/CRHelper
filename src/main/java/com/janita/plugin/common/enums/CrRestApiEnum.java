package com.janita.plugin.common.enums;

import com.janita.plugin.cr.setting.CrQuestionSetting;
import lombok.AllArgsConstructor;

/**
 * CrRestApiEnum
 *
 * @author zhucj
 * @since 20220324
 */
@AllArgsConstructor
public enum CrRestApiEnum {

    ADD("/cr/add"),

    UPDATE("/cr/update"),

    QUERY("/cr/query"),
    ;

    private final String url;

    public String getFullUrl() {
        String restApiDomain = CrQuestionSetting.getCrQuestionSettingFromCache().getRestApiDomain();
        return restApiDomain + url;
    }
}