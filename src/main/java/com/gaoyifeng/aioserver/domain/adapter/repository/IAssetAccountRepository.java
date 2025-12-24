package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.AssetAccountEntity;

/**
 * 用户资产账户仓储接口
 */
public interface IAssetAccountRepository {

    /**
     * 根据用户ID查询资产账户
     * @param userId 用户ID
     * @return 资产账户实体
     */
    AssetAccountEntity queryByUserId(String userId);
}
