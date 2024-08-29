create database if not exists byoj;

use byoj;

create table if not exists question
(
    id           bigint auto_increment comment '题目ID'
        primary key,
    title        varchar(256)                       not null comment '标题',
    content      text                               not null comment '题目内容',
    answer       text                               not null comment '题目答案',
    tags         varchar(256)                       null comment '题目标签',
    judge_case   text                               not null comment '判题用例',
    judge_config text                               not null comment '判题配置',
    submit_num   int      default 0                 null comment '总提交数',
    accept_num   int      default 0                 null comment '总通过数',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted   int      default 0                 not null comment '是否删除（0-未删除，1-删除）',
    constraint question_pk
        unique (title)
)
    comment '题目表';

create table if not exists question_submit
(
    id          bigint auto_increment comment '题目提交信息ID'
        primary key,
    language    varchar(128) default 'Java'            not null comment '语言',
    code        text                                   not null comment '代码',
    judge_info  text                                   null comment '判题信息（包括执行信息、占用内存、消耗时间）',
    status      int          default 0                 not null comment '判题状态',
    question_id bigint                                 not null comment '题目ID',
    user_id     bigint                                 not null comment '用户ID',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted  int          default 0                 not null comment '是否删除（0-未删除，1-删除）'
)
    comment '题目提交信息表';

create index idx_questionId
    on question_submit (question_id);

create index idx_userId
    on question_submit (user_id);

create table if not exists user
(
    id            bigint auto_increment comment '用户ID'
        primary key,
    user_account  varchar(256)                       not null comment '用户账号',
    user_password varchar(512)                       not null comment '用户密码',
    user_name     varchar(256)                       null comment '用户名',
    email         varchar(256)                       null comment '用户邮箱',
    user_role     tinyint  default 1                 not null comment '用户角色（0-管理员，1-普通用户）',
    status        tinyint  default 0                 not null comment '用户状态（0-启用，1-禁用）',
    salt          char(4)                            not null comment '固定四位的盐，用于加密',
    create_time   datetime default (now())           not null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_deleted    tinyint  default 0                 not null comment '是否删除（0-未删除，1-删除）'
)
    comment '用户表';

