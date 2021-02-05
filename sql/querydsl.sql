SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS qsl_post;
CREATE TABLE qsl_post(
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    title varchar(255) default null comment  '标题',
    content text default null comment '内容',
    author_id bigint default null comment '作者',
    create_by varchar(255) DEFAULT NULL COMMENT '创建者',
    update_by varchar(255) DEFAULT NULL COMMENT '更新者',
    create_time datetime DEFAULT NULL COMMENT '创建日期',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文章列表';

DROP TABLE IF exists qsl_author;
CREATE TABLE qsl_author(
                           id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                           name varchar(255) default null COMMENT '姓名',
                           age int default null comment '年龄',
                           create_by varchar(255) DEFAULT NULL COMMENT '创建者',
                           update_by varchar(255) DEFAULT NULL COMMENT '更新者',
                           create_time datetime DEFAULT NULL COMMENT '创建日期',
                           update_time datetime DEFAULT NULL COMMENT '更新时间',
                           primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='作者';

drop table if exists qsl_tag;
create table qsl_tag(
                        id bigint(20) not null auto_increment comment 'ID',
                        name varchar(255) default null comment '标签名',
                        create_by varchar(255) DEFAULT NULL COMMENT '创建者',
                        update_by varchar(255) DEFAULT NULL COMMENT '更新者',
                        create_time datetime DEFAULT NULL COMMENT '创建日期',
                        update_time datetime DEFAULT NULL COMMENT '更新时间',
                        primary key (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='标签列表';

drop table if exists qsl_post_tag;
create table  qsl_post_tag(
                              post_id bigint not null comment 'post id',
                              tag_id  bigint not null comment 'tag id',
                              primary key (post_id, tag_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='中间表';



