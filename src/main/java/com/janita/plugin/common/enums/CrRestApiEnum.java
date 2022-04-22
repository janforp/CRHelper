package com.janita.plugin.common.enums;

import com.intellij.ide.util.PropertiesComponent;
import com.janita.plugin.common.constant.PersistentKeys;
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
        String domain = PropertiesComponent.getInstance().getValue(PersistentKeys.REST_API_DOMAIN);
        return domain + url;
    }
}