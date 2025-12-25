package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.api.dto.asset.response.AssetAccountResponseDto;

/**
 * 用户资产账户服务接口
 */
public interface IAssetAccountService {

    /**
     * 查询用户资产总览
     * @param userId 用户ID
     * @return 资产账户VO
     */
    AssetAccountResponseDto queryAssetAccount(String userId);
}
