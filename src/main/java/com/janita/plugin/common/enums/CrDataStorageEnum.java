package com.janita.plugin.common.enums;

import com.intellij.openapi.ui.ValidationInfo;
import com.janita.plugin.common.domain.Pair;
import com.janita.plugin.common.util.DruidDbUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CrDataStorageEnum
 *
 * @author zhucj
 * @since 20220324
 */
@AllArgsConstructor
@Getter
public enum CrDataStorageEnum {

    LOCAL_CACHE(true, "本地缓存") {
        @Override
        public ValidationInfo check(JTextField... fields) {
            return null;
        }
    },

    REST_API(false, "REST接口") {
        @Override
        public ValidationInfo check(JTextField... fields) {
            if (fields == null || fields.length == 0 || !fields[0].getText().startsWith("http")) {
                return new ValidationInfo("请填写正确的rest接口域名");
            }
            return null;
        }
    },

    SQLITE_DB(true, "本地sqlite数据库") {
        @Override
        public ValidationInfo check(JTextField... fields) {
            return null;
        }
    },

    MYSQL_DB(true, "远程mysql数据库") {
        @Override
        public ValidationInfo check(JTextField... fields) {
            if (fields == null || fields.length != 3) {
                return new ValidationInfo("数据库地址，用户名，密码填写不完整");
            }
            JTextField urlField = fields[0];
            JTextField usernameField = fields[1];
            JTextField pwdField = fields[2];
            String url = urlField.getText();
            String username = usernameField.getText();
            String pwd = pwdField.getText();
            if (url == null || url.trim().length() == 0) {
                return new ValidationInfo("请输入数据库地址");
            }
            if (username == null || username.trim().length() == 0) {
                return new ValidationInfo("请输数据库用户名");
            }
            if (pwd == null || pwd.trim().length() == 0) {
                return new ValidationInfo("请输数据库密码");
            }
            return null;
        }

        @Override
        public Pair<Boolean, String> checkConnect(JTextField... fields) {
            JTextField urlField = fields[0];
            JTextField usernameField = fields[1];
            JTextField pwdField = fields[2];
            String url = urlField.getText();
            String username = usernameField.getText();
            String pwd = pwdField.getText();
            DataSource dataSource = DruidDbUtils.getDataSource(url, username, pwd);
            try {
                Connection connection = dataSource.getConnection();
                if (!connection.isClosed()) {
                    return Pair.of(true, "连接成功");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return Pair.of(false, "连接失败");
            }
            return Pair.of(true, "连接成功");
        }
    },
    ;

    private final boolean support;

    private final String desc;

    public static Set<CrDataStorageEnum> getSupportSet() {
        return Arrays.stream(CrDataStorageEnum.values()).filter(CrDataStorageEnum::isSupport).collect(Collectors.toSet());
    }

    public abstract ValidationInfo check(JTextField... fields);

    public Pair<Boolean, String> checkConnect(JTextField... fields) {
        return Pair.of(true, "连接成功");
    }

    public static CrDataStorageEnum getByDesc(String desc) {
        for (CrDataStorageEnum storageEnum : CrDataStorageEnum.values()) {
            if (storageEnum.getDesc().equals(desc)) {
                return storageEnum;
            }
        }
        return null;
    }
}