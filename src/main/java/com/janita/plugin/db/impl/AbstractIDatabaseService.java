package com.janita.plugin.db.impl;

import com.janita.plugin.db.IDatabaseService;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * AbstractIDatabaseService
 *
 * @author zhucj
 * @since 20220324
 */
public abstract class AbstractIDatabaseService implements IDatabaseService {

    protected DataSource source;

    protected Connection connection;

    protected AbstractIDatabaseService() {
        onDatasourceChange();
    }

    protected void createFileAndDir() {
        // empty
    }

    @Override
    public void onDatasourceChange() {
        this.source = null;
        this.connection = null;
        this.source = initDataSource();
        this.connection = getConnection();
        this.initTable();
        // 如果不存在,创建DB文件
        createFileAndDir();
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = source.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if (connection.isClosed()) {
                try {
                    connection = source.getConnection();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

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

    /**
     * 数据源初始化
     *
     * @return 数据源初始化
     */
    protected abstract DataSource initDataSource();

    /**
     * 创建表
     *
     * @return sql
     */
    protected abstract String getTableSql();
}