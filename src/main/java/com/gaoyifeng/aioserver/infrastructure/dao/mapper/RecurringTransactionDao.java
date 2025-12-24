package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.RecurringTransactionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 固定收支配置DAO
 */
@Mapper
public interface RecurringTransactionDao {

    /**
     * 新增固定收支配置
     * @param po 固定收支配置PO
     */
    void insert(@Param("po") RecurringTransactionPO po);

    /**
     * 删除固定收支配置(软删除)
     * @param id 配置ID
     * @param userId 用户ID
     */
    void softDelete(@Param("id") String id, @Param("userId") String userId);

    /**
     * 更新固定收支配置
     * @param po 固定收支配置PO
     */
    void update(@Param("po") RecurringTransactionPO po);

    /**
     * 根据ID查询固定收支配置
     * @param id 配置ID
     * @return 固定收支配置PO
     */
    RecurringTransactionPO queryById(@Param("id") String id);

    /**
     * 查询用户的所有固定收支配置
     * @param userId 用户ID
     * @return 固定收支配置PO列表
     */
    List<RecurringTransactionPO> queryByUserId(@Param("userId") String userId);
}
