package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.TemporaryTransactionEntity;

import java.util.List;

/**
 * 临时收支记录仓储接口
 */
public interface ITemporaryTransactionRepository {

    void save(TemporaryTransactionEntity entity);

    void delete(String id, String userId);

    void update(TemporaryTransactionEntity entity);

    TemporaryTransactionEntity queryById(String id);

    List<TemporaryTransactionEntity> queryByUserId(String userId);
}
