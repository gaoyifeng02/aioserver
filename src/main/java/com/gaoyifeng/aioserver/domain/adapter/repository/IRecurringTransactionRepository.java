package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.RecurringTransactionEntity;

import java.util.List;

/**
 * 固定收支配置仓储接口
 */
public interface IRecurringTransactionRepository {

    /**
     * 新增固定收支配置
     * @param entity 固定收支配置实体
     */
    void save(RecurringTransactionEntity entity);

    /**
     * 删除固定收支配置(软删除)
     * @param id 配置ID
     * @param userId 用户ID
     */
    void delete(String id, String userId);

    /**
     * 更新固定收支配置
     * @param entity 固定收支配置实体
     */
    void update(RecurringTransactionEntity entity);

    /**
     * 根据ID查询固定收支配置
     * @param id 配置ID
     * @return 固定收支配置实体
     */
    RecurringTransactionEntity queryById(String id);

    /**
     * 查询用户的所有固定收支配置
     * @param userId 用户ID
     * @return 固定收支配置列表
     */
    List<RecurringTransactionEntity> queryByUserId(String userId);
}
