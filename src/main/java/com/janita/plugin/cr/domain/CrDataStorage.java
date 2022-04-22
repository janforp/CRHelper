package com.janita.plugin.cr.domain;

import com.janita.plugin.common.enums.CrDataStorageEnum;
import com.janita.plugin.db.impl.SqliteDatabaseServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

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
public class CrDataStorage {

    private CrDataStorageEnum storageWay;

    private String dbUrl;

    private String dbPwd;

    /**
     * @see com.janita.plugin.common.enums.CrRestApiEnum
     */
    private String restApiDomain;

    public static boolean checkValid(CrDataStorage storage) {
        if (storage == null) {
            return false;
        }
        Set<CrDataStorageEnum> supportSet = CrDataStorageEnum.getSupportSet();
        CrDataStorageEnum storageWay = storage.getStorageWay();
        if (!supportSet.contains(storageWay)) {
            // 如果之前设置的方式已经不支持了，则再次弹出
            return false;
        }
        if (CrDataStorageEnum.LOCAL_CACHE == storageWay) {
            return true;
        }
        if (CrDataStorageEnum.SQLITE_DB == storageWay) {
            Connection connection = SqliteDatabaseServiceImpl.getInstance().getConnection();
            try {
                return !connection.isClosed();
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        if (CrDataStorageEnum.MYSQL_DB == storageWay) {
            return StringUtils.isNoneBlank(storage.getDbUrl(), storage.getDbPwd());
        }
        if (CrDataStorageEnum.REST_API == storageWay) {
            return StringUtils.isNotBlank(storage.getRestApiDomain());
        }
        return false;
    }
}
