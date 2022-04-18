package com.janita.plugin.common.enums;

import com.janita.plugin.cr.domain.CrDataStorage;
import com.janita.plugin.cr.persistent.CrDataStoragePersistent;
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
        CrDataStorage storageWay = CrDataStoragePersistent.getInstance().getState();
        String restDomain = storageWay.getRestApiDomain();
        return restDomain + url;
    }
}
