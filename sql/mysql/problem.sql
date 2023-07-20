create table problem_answer
(
    id              bigint auto_increment comment '主键'
        primary key,
    problem_id      bigint                                not null comment '问题编号',
    answer_id       varchar(64) default ''                not null comment '回答人工号',
    answer_name     varchar(128)                          not null comment '回答人姓名',
    answer_date     datetime                              not null comment '回答时间',
    answer_content  text                                  not null comment '回答内容',
    answer_file_id  bigint                                null comment '文件',
    answer_attached varchar(1024)                         null comment '回答附件',
    answer_score    int                                   null comment '回答评分',
    creator         varchar(64) default ''                not null comment '创建人',
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(64) default ''                not null comment '更新人',
    update_time     datetime    default CURRENT_TIMESTAMP not null comment '更新时间',
    deleted         bit         default b'0'              not null comment '删除',
    tenant_id       bigint      default 0                 not null comment '租户'
)
    comment '问题回答';

create table problem_info
(
    id               bigint auto_increment comment '主键'
        primary key,
    detail_id        varchar(64)                           not null comment '问题详情编号',
    pub_work_id      varchar(128)                          not null comment '提出人工号',
    pub_user_name    varchar(256)                          not null comment '提出人姓名',
    problem_name     varchar(512)                          not null comment '问题名称',
    problem_describe text                                  not null comment '问题描述',
    problem_file_id  bigint                                null comment '文件id',
    problem_attached varchar(1024)                         null comment '问题附件',
    sub_work_id      varchar(1024)                         not null comment '指定回答人工号',
    problem_type     varchar(16) default '0'               not null comment '问题分类0-未分类 1-市场2-供应链3-竞争对手4-客户5-政策',
    creator          varchar(64) default ''                not null comment '创建人',
    create_time      datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(64) default ''                not null comment '更新人',
    update_time      datetime    default CURRENT_TIMESTAMP not null comment '更新时间',
    deleted          bit         default b'0'              not null comment '删除',
    tenant_id        bigint      default 0                 not null comment '租户'
)
    comment '问题信息';

