package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.AssetAccountPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户资产账户DAO
 */
@Mapper
public interface AssetAccountDao {

    /**
     * 根据用户ID查询资产账户
     * @param userId 用户ID
     * @return 资产账户PO
     */
    AssetAccountPO queryByUserId(@Param("userId") String userId);
}
