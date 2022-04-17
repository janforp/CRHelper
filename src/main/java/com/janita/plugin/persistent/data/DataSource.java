package com.janita.plugin.persistent.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * DataSource
 *
 * @author zhucj
 * @since 20220324
 */
@Data
@AllArgsConstructor
@ToString
public class DataSource {

    private String dataUrl;

    private String dataPwd;
}
