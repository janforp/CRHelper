package com.janita.plugin.db;

import java.sql.Connection;

/**
 * IDatabaseService
 *
 * @author zhucj
 * @since 20220324
 */
public interface IDatabaseService {

    /**
     * 检查配置
     *
     * @param url url
     * @param username 用户
     * @param pwd 密码
     * @return 配置是否正确
     */
    boolean checkParam(String url, String username, String pwd);

    /**
     * 获取链接
     *
     * @return 数据库链接
     */
    Connection getConnectDirectly();

    /**
     * 配置发生变化
     */
    void onDatasourceChange();

    /**
     * 获取链接
     *
     * @return 数据库链接
     */
    Connection getConnection();

    /**
     * 释放资源
     */
    void closeResource();
}