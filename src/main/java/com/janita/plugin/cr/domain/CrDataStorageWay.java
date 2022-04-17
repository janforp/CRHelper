package com.janita.plugin.cr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CrDataStorageWay
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrDataStorageWay {

    private String wayName;

    private String dataUrl;

    private String dataPwd;

    private String restDomain;
}
