create table IF NOT EXISTS cr_question
(
    id                     int auto_increment not null comment '自增id' primary key,
    project_name           varchar(20)        not null comment '项目名称',
    file_path              varchar(255)       not null comment '文件路径',
    file_name              varchar(255)       null comment '文件名',
    language               varchar(50)        null comment '语言，如java',
    type                   varchar(20)        not null comment '问题类型',
    level                  char(2)            not null comment '该问题的级别,类似复杂程度,数字越大问题越复杂',
    state                  VARCHAR(10)        not null comment '状态,未解决,已解决,重复问题,已关闭',
    assign_from            VARCHAR(50)        not null comment '提出问题的人',
    assign_to              VARCHAR(50)        not null comment '接收问题的人',
    question_code          mediumtext         not null comment '问题代码',
    better_code            mediumtext         not null comment '建议写法',
    description            mediumtext         not null comment '描述',
    create_git_branch_name varchar(20)        not null comment '创建git分支名称',
    solve_git_branch_name  varchar(20) default null comment '解决git分支名称',
    create_time            varchar(20)        not null comment '提问时间',
    solve_time             varchar(20) default null comment '解决时间',
    offset_start           varchar(20) default null comment '起始offset',
    offset_end             varchar(20) default null comment '结束offset'
) comment 'cr问题';