package com.janita.plugin.cr.domain;

import com.janita.plugin.common.constant.CrConstants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

/**
 * CrDataStorageWay
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class CrDataStorageWay {

    private String storageWay;

    private String dataUrl;

    private String dataPwd;

    private String restDomain;

    public static boolean checkValid(CrDataStorageWay way) {
        if (way == null) {
            return false;
        }
        if (CrConstants.LOCAL_CACHE.equals(way.getStorageWay())) {
            return true;
        }
        if (CrConstants.DB_WAY.equals(way.getStorageWay())) {
            return StringUtils.isNoneBlank(way.getDataUrl(), way.getDataPwd());
        }
        if (CrConstants.REST_WAY.equals(way.getStorageWay())) {
            return StringUtils.isNotBlank(way.getRestDomain());
        }
        return false;
    }
}
