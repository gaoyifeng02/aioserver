package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.SavingsPlanPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 存款计划DAO
 */
@Mapper
public interface SavingsPlanDao {

    /**
     * 新增存款计划
     * @param po 存款计划PO
     */
    void insert(@Param("po") SavingsPlanPO po);

    /**
     * 删除存款计划(软删除)
     * @param id 计划ID
     * @param userId 用户ID
     */
    void softDelete(@Param("id") String id, @Param("userId") String userId);

    /**
     * 更新存款计划
     * @param po 存款计划PO
     */
    void update(@Param("po") SavingsPlanPO po);

    /**
     * 根据ID查询存款计划
     * @param id 计划ID
     * @return 存款计划PO
     */
    SavingsPlanPO queryById(@Param("id") String id);

    /**
     * 查询用户的所有存款计划
     * @param userId 用户ID
     * @return 存款计划PO列表
     */
    List<SavingsPlanPO> queryByUserId(@Param("userId") String userId);
}
