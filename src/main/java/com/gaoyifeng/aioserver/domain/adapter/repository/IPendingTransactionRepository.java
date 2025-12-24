package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.PendingTransactionEntity;

import java.util.List;

/**
 * 待入账仓储接口
 */
public interface IPendingTransactionRepository {

    void save(PendingTransactionEntity entity);

    void delete(String id, String userId);

    void update(PendingTransactionEntity entity);

    PendingTransactionEntity queryById(String id);

    List<PendingTransactionEntity> queryByUserId(String userId);
}
