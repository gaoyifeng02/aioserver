package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.api.service.IAssetAccountService;
import com.gaoyifeng.aioserver.domain.adapter.repository.IAssetAccountRepository;
import com.gaoyifeng.aioserver.domain.model.entity.AssetAccountEntity;
import com.gaoyifeng.aioserver.types.vo.AssetAccountVO;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.math.BigDecimal;

/**
 * 用户资产账户服务实现
 */
@Service
public class AssetAccountService implements IAssetAccountService {

    @Resource
    private IAssetAccountRepository assetAccountRepository;

    @Override
    public AssetAccountVO queryAssetAccount(String userId) {
        // 1. 查询资产账户
        AssetAccountEntity entity = assetAccountRepository.queryByUserId(userId);

        if (entity == null) {
            return null;
        }

        // 2. 转换为VO
        AssetAccountVO vo = new AssetAccountVO();
        vo.setUserId(entity.getUserId());
        vo.setTotalBalance(entity.getTotalBalance());
        vo.setTotalSavings(entity.getTotalSavings());
        vo.setTotalAssets(entity.getTotalBalance().add(entity.getTotalSavings()));

        return vo;
    }
}
