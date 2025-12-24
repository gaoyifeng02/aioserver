package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.types.vo.AssetAccountVO;

/**
 * 用户资产账户服务接口
 */
public interface IAssetAccountService {

    /**
     * 查询用户资产总览
     * @param userId 用户ID
     * @return 资产账户VO
     */
    AssetAccountVO queryAssetAccount(String userId);
}
