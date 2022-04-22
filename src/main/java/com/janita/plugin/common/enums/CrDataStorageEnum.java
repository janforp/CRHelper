package com.janita.plugin.common.enums;

import com.intellij.openapi.ui.ValidationInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.*;
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

    MYSQL_DB(false, "数据库") {
        @Override
        public ValidationInfo check(JTextField... fields) {
            if (fields == null || fields.length == 0) {
                return new ValidationInfo("请输入数据库地址");
            }
            JTextField urlField = fields[0];
            if (fields.length < 2) {
                return new ValidationInfo("请输数据库密码");
            }
            JTextField pwdField = fields[1];
            String url = urlField.getText();
            String pwd = pwdField.getText();
            if (url == null || url.trim().length() == 0) {
                return new ValidationInfo("请输入数据库地址");
            }
            if (pwd == null || pwd.trim().length() == 0) {
                return new ValidationInfo("请输数据库密码");
            }
            return null;
        }
    },
    ;

    private final boolean support;

    private final String desc;

    public static Set<CrDataStorageEnum> getSupportSet() {
        return Arrays.stream(CrDataStorageEnum.values()).filter(CrDataStorageEnum::isSupport).collect(Collectors.toSet());
    }

    public abstract ValidationInfo check(JTextField... fields);
}