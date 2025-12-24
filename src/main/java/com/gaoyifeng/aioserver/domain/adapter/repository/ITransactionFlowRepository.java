package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.TransactionFlowEntity;

import java.util.List;

/**
 * 资产流水仓储接口
 */
public interface ITransactionFlowRepository {

    /**
     * 根据ID查询资产流水
     * @param id 流水ID
     * @return 资产流水实体
     */
    TransactionFlowEntity queryById(String id);

    /**
     * 查询用户的所有资产流水
     * @param userId 用户ID
     * @return 资产流水列表
     */
    List<TransactionFlowEntity> queryByUserId(String userId);
}
