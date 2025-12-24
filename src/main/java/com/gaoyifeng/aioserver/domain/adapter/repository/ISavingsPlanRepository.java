package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.SavingsPlanEntity;

import java.util.List;

/**
 * 存款计划仓储接口
 */
public interface ISavingsPlanRepository {

    /**
     * 新增存款计划
     * @param entity 存款计划实体
     */
    void save(SavingsPlanEntity entity);

    /**
     * 删除存款计划(软删除)
     * @param id 计划ID
     * @param userId 用户ID
     */
    void delete(String id, String userId);

    /**
     * 更新存款计划
     * @param entity 存款计划实体
     */
    void update(SavingsPlanEntity entity);

    /**
     * 根据ID查询存款计划
     * @param id 计划ID
     * @return 存款计划实体
     */
    SavingsPlanEntity queryById(String id);

    /**
     * 查询用户的所有存款计划
     * @param userId 用户ID
     * @return 存款计划列表
     */
    List<SavingsPlanEntity> queryByUserId(String userId);
}
