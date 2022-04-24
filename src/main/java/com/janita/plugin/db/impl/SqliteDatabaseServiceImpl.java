package com.janita.plugin.db.impl;

import com.janita.plugin.common.constant.PluginConstant;
import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.nio.file.Files;

/**
 * SqliteDatabaseServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class SqliteDatabaseServiceImpl extends AbstractIDatabaseService {

    private static final String DATABASE_URL = "jdbc:sqlite:" + PluginConstant.DB_FILE_PATH;

    private SqliteDatabaseServiceImpl() {

    }

    @Override
    protected BasicDataSource initDataSource() {
        //创建了DBCP的数据库连接池
        BasicDataSource source = new BasicDataSource();
        try {
            //设置基本信息
            source.setMaxActive(1);
            source.setDriverClassName(PluginConstant.DbDrivers.SQLITE_DATABASE_DRIVER);
            source.setUrl(DATABASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    @Override
    protected String getTableSql() {
        return "create table IF NOT EXISTS cr_question"
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
                + "    offset_start           INTEGER, "
                + "    offset_end             INTEGER, "
                + "    is_delete            INTEGER "
                + ") ";
    }

    /**
     * 如果不存在目录和文件就创建
     */
    protected void createFileAndDir() {
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
}