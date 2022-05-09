package com.ikyxxs.idworker.mapper;

import org.apache.ibatis.annotations.Param;

public interface IdWorkerMapper {

    /**
     * insert
     *
     * @param bizType 业务类型
     * @param init 初始值
     */
    int insert(@Param("bizType") String bizType, @Param("init") long init);

    /**
     * incrBy
     *
     * @param bizType 业务类型
     * @param num 增长步长
     */
    int incrBy(@Param("bizType") String bizType, @Param("num") long num);

    /**
     * selectForUpdate
     *
     * @param bizType 业务类型
     * @return 当前步数
     */
    Long selectForUpdate(@Param("bizType") String bizType);
}
