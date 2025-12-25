package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.api.dto.asset.response.AssetAccountResponseDto;
import com.gaoyifeng.aioserver.api.IAssetAccountService;
import com.gaoyifeng.aioserver.domain.adapter.repository.IAssetAccountRepository;
import com.gaoyifeng.aioserver.domain.model.entity.AssetAccountEntity;
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
    public AssetAccountResponseDto queryAssetAccount(String userId) {
        // 1. 查询资产账户
        AssetAccountEntity entity = assetAccountRepository.queryByUserId(userId);

        if (entity == null) {
            return null;
        }

        // 2. 转换为VO
        AssetAccountResponseDto vo = new AssetAccountResponseDto();
        vo.setUserId(entity.getUserId());
        vo.setTotalBalance(entity.getTotalBalance());
        vo.setTotalSavings(entity.getTotalSavings());
        vo.setTotalAssets(entity.getTotalBalance().add(entity.getTotalSavings()));

        return vo;
    }
}
