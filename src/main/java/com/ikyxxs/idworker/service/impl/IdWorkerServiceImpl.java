package com.ikyxxs.idworker.service.impl;

import com.ikyxxs.idworker.enums.IdWorkerType;
import com.ikyxxs.idworker.mapper.IdWorkerMapper;
import com.ikyxxs.idworker.service.IdWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 发号器服务实现
 */
@Service
public class IdWorkerServiceImpl implements IdWorkerService {

    @Resource
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private IdWorkerMapper idWorkerMapper;

    private final ConcurrentMap<String, Queue<Long>> bizIdSequenceQueue = new ConcurrentHashMap<>();

    @Override
    public Long getNextID(IdWorkerType type) {
        String bizType = type.getType();

        Queue<Long> idQueue = bizIdSequenceQueue.get(bizType);
        if (idQueue == null) {
            idQueue = new LinkedList<>();
            Queue<Long> temp = bizIdSequenceQueue.putIfAbsent(bizType, idQueue);
            if (temp != null) {
                idQueue = temp;
            }
        }
        synchronized (idQueue) {
            if (idQueue.isEmpty()) {
                int batchSize = getBatchSize(type);
                Long firstId = getMysqlID(bizType, batchSize);
                for (int i = 0; i < batchSize; i++) {
                    idQueue.add(firstId++);
                }
            }
            return idQueue.poll();
        }
    }

    @Override
    public Long getGroupIDBySize(IdWorkerType type, int batchSize) {
        return getMysqlID(type.getType(), batchSize);
    }

    private Long getMysqlID(String bizType, int batchSize) {
        DefaultTransactionDefinition dd = new DefaultTransactionDefinition();
        dd.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        TransactionStatus ts = transactionManager.getTransaction(dd);
        try {
            Long mysqlId = idWorkerMapper.selectForUpdate(bizType);
            if (mysqlId == null) {
                mysqlId = 1L;
                long initNum = mysqlId + batchSize;
                idWorkerMapper.insert(bizType, initNum);
                return mysqlId;
            }
            int updateRows = idWorkerMapper.incrBy(bizType, batchSize);
            if (updateRows > 0) {
                return mysqlId;
            } else {
                throw new IllegalStateException("key:" + bizType + "does not exist!");
            }
        } catch (Exception e) {
            ts.setRollbackOnly();
            throw e;
        } finally {
            transactionManager.commit(ts);
        }
    }

    private int getBatchSize(IdWorkerType type) {
        switch (type) {
            case ORDER_ID:
                return 100;
            default:
                return 1;
        }
    }
}
