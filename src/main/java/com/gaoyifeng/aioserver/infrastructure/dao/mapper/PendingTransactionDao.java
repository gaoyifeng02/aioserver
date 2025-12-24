package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.PendingTransactionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PendingTransactionDao {
    void insert(@Param("po") PendingTransactionPO po);
    void softDelete(@Param("id") String id, @Param("userId") String userId);
    void update(@Param("po") PendingTransactionPO po);
    PendingTransactionPO queryById(@Param("id") String id);
    List<PendingTransactionPO> queryByUserId(@Param("userId") String userId);
}
