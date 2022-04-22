package com.janita.plugin.db.impl;

import com.janita.plugin.db.IDatabaseService;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * AbstractIDatabaseService
 *
 * @author zhucj
 * @since 20220324
 */
public abstract class AbstractIDatabaseService implements IDatabaseService {

    protected BasicDataSource source;

    protected Connection connection;

    protected AbstractIDatabaseService() {
        source = initDataSource();
        // 如果不存在,创建DB文件
        createFileAndDir();
        // 如果表不存在,创建表
        initTable();
    }

    protected void createFileAndDir() {
        // empty
    }

    protected abstract BasicDataSource initDataSource();

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = source.getConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (connection.isClosed()) {
                try {
                    connection = source.getConnection();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    protected abstract String getTableSql();

    private void initTable() {
        String createQuestionSQL = getTableSql();
        try {
            QueryRunner queryRunner = new QueryRunner(source);
            queryRunner.update(createQuestionSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeResource() {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}