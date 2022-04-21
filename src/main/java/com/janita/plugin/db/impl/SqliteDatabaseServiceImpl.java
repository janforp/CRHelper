package com.janita.plugin.db.impl;

import com.intellij.openapi.application.ApplicationManager;
import com.janita.plugin.common.constant.PluginConstant;
import com.janita.plugin.db.IDatabaseService;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * SqliteDatabaseServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class SqliteDatabaseServiceImpl implements IDatabaseService {

    private static final String DATABASE_DRIVER = "org.sqlite.JDBC";

    private static final String DATABASE_URL = "jdbc:sqlite:" + PluginConstant.DB_FILE_PATH;

    public static SqliteDatabaseServiceImpl getInstance() {
        return ApplicationManager.getApplication().getService(SqliteDatabaseServiceImpl.class);
    }

    private BasicDataSource source;

    public SqliteDatabaseServiceImpl() {
        try {
            //创建了DBCP的数据库连接池
            source = new BasicDataSource();
            //设置基本信息
            source.setMaxActive(1);
            source.setDriverClassName(DATABASE_DRIVER);
            source.setUrl(DATABASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果不存在,创建DB文件
        createFileAndDir();
        // 如果表不存在,创建表
        initTable();
    }

    /**
     * 如果不存在目录和文件就创建
     */
    private void createFileAndDir() {
        //"C:\Users\Administrator\.ideaCRHelperFile"
        if (!Files.exists(PluginConstant.PROJECT_DB_DIRECTORY_PATH)) {
            try {
                Files.createDirectories(PluginConstant.PROJECT_DB_DIRECTORY_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //"C:\Users\Administrator\.ideaCRHelperFileotebooks.db"
        if (!Files.exists(PluginConstant.DB_FILE_PATH)) {
            try {
                Files.createFile(PluginConstant.DB_FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public BasicDataSource getSource() {
        return source;
    }

    @Override
    public Connection getConnection() {
        try {
            return source.getConnection();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void initTable() {

        String createQuestionSQL = "create table IF NOT EXISTS cr_question"
                + "("
                + "    id                     INTEGER   PRIMARY KEY AUTOINCREMENT, "
                + "    project_name           TEXT, "
                + "    file_path              TEXT, "
                + "    file_name              TEXT, "
                + "    language               TEXT, "
                + "    type                   TEXT, "
                + "    level                  TEXT, "
                + "    state                  TEXT, "
                + "    assign_from            TEXT, "
                + "    assign_to              TEXT, "
                + "    question_code          TEXT, "
                + "    better_code            TEXT, "
                + "    description            TEXT, "
                + "    create_git_branch_name TEXT, "
                + "    solve_git_branch_name  TEXT, "
                + "    create_time            TEXT, "
                + "    solve_time             TEXT, "
                + "    offset_start           TEXT, "
                + "    offset_end             TEXT, "
                + "    is_delete            INTEGER "
                + ") ";

        try {
            QueryRunner queryRunner = new QueryRunner(getSource());
            queryRunner.update(createQuestionSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeResource(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}