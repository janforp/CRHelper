package com.janita.plugin.common.constant;

/**
 * PersistentKeys
 *
 * @author zhucj
 * @since 20220324
 */
public class PersistentKeys {

    public static class CrDataStorageConfig {

        public static final String CR_DATA_STORAGE_WAY = "com.janita.plugin.cr.data.storage.way";

        public static final String MYSQL_URL = "com.janita.plugin.mysql.url";

        public static final String MYSQL_USERNAME = "com.janita.plugin.mysql.username";

        public static final String MYSQL_PWD = "com.janita.plugin.mysql.pwd";

        public static final String REST_API_DOMAIN = "com.janita.plugin.rest.api.domain";
    }

    public static class WeChatRobotConfig {

        /**
         * 群聊
         *
         * 80403b99-179c-4a9c-a0cc-3160c705f53a
         */
        public static final String WE_CHAT_GROUP_ROBOT_ID = "com.janita.plugin.cr.wechat.group.id";
    }
}