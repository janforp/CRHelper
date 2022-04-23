package com.janita.plugin.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * DruidDbUtils
 *
 * @author zhucj
 * @since 20220324
 */
public class DruidDbUtils {

    public static DataSource getDataSource(String url, String username, String pwd) throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("url", url);
        map.put("username", username);
        map.put("password", pwd);
        map.put("maxWait", "5000");
        map.put("maxActive", "1");
        map.put("minIdle", "0");
        DataSource dataSource = DruidDataSourceFactory.createDataSource(map);
        dataSource.setLoginTimeout(10);
        return dataSource;
    }
}