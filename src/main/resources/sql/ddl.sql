CREATE TABLE `tb_idworker` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `biz_type` varchar(64) NOT NULL COMMENT '业务类型',
    `counter` bigint NOT NULL COMMENT '业务ID计数器',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_biz_type` (`biz_type`),
    KEY `idx_gmt` (`gmt_create`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPRESSED COMMENT='业务ID计数器';
