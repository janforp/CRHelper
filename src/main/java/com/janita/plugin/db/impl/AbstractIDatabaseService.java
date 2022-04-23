package com.janita.plugin.db.impl;

import com.janita.plugin.db.IDatabaseService;
import com.mysql.cj.jdbc.ConnectionImpl;
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

    public static final Connection INVALID_CONNECT = new C();

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
        this.closeResource();
        this.connection = null;
        this.source = initDataSource();
        this.connection = getConnection();
        this.initTable();
        // 如果不存在,创建DB文件
        createFileAndDir();
    }

    @Override
    public boolean checkParam(String url, String username, String pwd) {
        return false;
    }

    @Override
    public Connection getConnection() {
        if (connection == null || connection == INVALID_CONNECT) {
            try {
                connection = source.getConnection();
            } catch (Exception e) {
                e.printStackTrace();
                connection = INVALID_CONNECT;
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
        if (connection == null) {
            return INVALID_CONNECT;
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

    private static class C extends ConnectionImpl {

        @Override
        public String toString() {
            return "数据库配置异常";
        }
    }
}