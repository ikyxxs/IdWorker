package com.ikyxxs.idworker.service;

import com.ikyxxs.idworker.enums.IdWorkerType;

/**
 * 发号器服务接口
 */
public interface IdWorkerService {

    /**
     * 获取下一个ID
     *
     * @param type 类型
     * @return ID
     */
    Long getNextID(IdWorkerType type);

    Long getGroupIDBySize(IdWorkerType type, int batchSize);
}
