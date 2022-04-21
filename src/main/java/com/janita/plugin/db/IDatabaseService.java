package com.janita.plugin.db;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;

/**
 * IDatabaseService
 *
 * @author zhucj
 * @since 20220324
 */
public interface IDatabaseService {

    /**
     * 获取数据源
     *
     * @return 获取数据源
     */
    BasicDataSource getSource();

    /**
     * 获取链接
     *
     * @return 数据库链接
     */
    Connection getConnection();

    /**
     * 创建表
     */
    void initTable();

    /**
     * 释放资源
     */
    void closeResource();
}