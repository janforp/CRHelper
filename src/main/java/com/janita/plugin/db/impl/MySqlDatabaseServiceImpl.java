package com.janita.plugin.db.impl;

import com.janita.plugin.common.util.DruidDbUtils;
import com.janita.plugin.cr.setting.CrQuestionSetting;

import javax.sql.DataSource;

/**
 * MySqlDatabaseServiceImpl
 *
 * @author zhucj
 * @since 20220324
 */
public class MySqlDatabaseServiceImpl extends AbstractIDatabaseService {

    private MySqlDatabaseServiceImpl() {

    }

    @Override
    protected DataSource initDataSource() {
        CrQuestionSetting setting = CrQuestionSetting.getCrQuestionSettingFromCache();
        try {
            source = DruidDbUtils.getDataSource(setting.getDbUrl(), setting.getDbUsername(), setting.getDbPwd());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return source;
    }

    public boolean checkParam(String url, String username, String pwd) {
        try {
            source = DruidDbUtils.getDataSource(url, username, pwd);
            connection = source.getConnection();
            return !connection.isClosed();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected String getTableSql() {
        return "create table IF NOT EXISTS cr_question\n"
                + "(\n"
                + "    id                     int auto_increment not null comment '自增id' primary key,\n"
                + "    project_name           varchar(20)        not null comment '项目名称',\n"
                + "    file_path              varchar(255)       not null comment '文件路径',\n"
                + "    file_name              varchar(255)       null comment '文件名',\n"
                + "    language               varchar(50)        null comment '语言，如java',\n"
                + "    type                   varchar(20)        not null comment '问题类型',\n"
                + "    level                  char(2)            not null comment '该问题的级别,类似复杂程度,数字越大问题越复杂',\n"
                + "    state                  VARCHAR(10)        not null comment '状态,未解决,已解决,重复问题,已关闭',\n"
                + "    assign_from            VARCHAR(50)        not null comment '提出问题的人',\n"
                + "    assign_to              VARCHAR(50)        not null comment '接收问题的人',\n"
                + "    question_code          mediumtext         not null comment '问题代码',\n"
                + "    better_code            mediumtext         not null comment '建议写法',\n"
                + "    description            mediumtext         not null comment '描述',\n"
                + "    create_git_branch_name varchar(20)        not null comment '创建git分支名称',\n"
                + "    solve_git_branch_name  varchar(20) default null comment '解决git分支名称',\n"
                + "    create_time            varchar(20)        not null comment '提问时间',\n"
                + "    solve_time             varchar(20) default null comment '解决时间',\n"
                + "    offset_start           int(20) default null comment '起始offset',\n"
                + "    offset_end             int(20) default null comment '结束offset',\n"
                + "    is_delete              int(20) default 0 comment '逻辑删除'\n"
                + ") comment 'cr问题'";
    }
}
