package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.TransactionFlowPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资产流水DAO
 */
@Mapper
public interface TransactionFlowDao {

    /**
     * 根据ID查询资产流水
     * @param id 流水ID
     * @return 资产流水PO
     */
    TransactionFlowPO queryById(@Param("id") String id);

    /**
     * 查询用户的所有资产流水
     * @param userId 用户ID
     * @return 资产流水PO列表
     */
    List<TransactionFlowPO> queryByUserId(@Param("userId") String userId);
}
