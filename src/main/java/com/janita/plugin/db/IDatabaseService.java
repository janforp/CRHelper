package com.janita.plugin.db;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
     *
     * @param conn 链接
     * @param statement 语句
     * @param rs 结果
     */
    void closeResource(Connection conn, Statement statement, ResultSet rs);
}