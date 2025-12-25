package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.TemporaryTransactionPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemporaryTransactionDao {
    void insert(@Param("po") TemporaryTransactionPO po);
    void softDelete(@Param("id") String id, @Param("userId") String userId);
    void update(@Param("po") TemporaryTransactionPO po);
    TemporaryTransactionPO queryById(@Param("id") String id);
    List<TemporaryTransactionPO> queryByUserId(@Param("userId") String userId);
}
