SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS qsl_post;
CREATE TABLE qsl_post(
    id bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    title varchar(255) default null comment  '标题',
    content text default null comment '内容',
    create_by varchar(255) DEFAULT NULL COMMENT '创建者',
    update_by varchar(255) DEFAULT NULL COMMENT '更新者',
    create_time datetime DEFAULT NULL COMMENT '创建日期',
    update_time datetime DEFAULT NULL COMMENT '更新时间',
)ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='文章列表';

