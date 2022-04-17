package com.janita.plugin.cr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    private String wayName;

    private String dataUrl;

    private String dataPwd;

    private String restDomain;
}
