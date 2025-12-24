package com.gaoyifeng.aioserver.infrastructure.adapter.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.IAssetAccountRepository;
import com.gaoyifeng.aioserver.domain.model.entity.AssetAccountEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.AssetAccountDao;
import com.gaoyifeng.aioserver.infrastructure.dao.po.AssetAccountPO;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;

/**
 * 用户资产账户仓储实现
 */
@Repository
public class AssetAccountRepository implements IAssetAccountRepository {

    @Resource
    private AssetAccountDao assetAccountDao;

    @Override
    public AssetAccountEntity queryByUserId(String userId) {
        AssetAccountPO po = assetAccountDao.queryByUserId(userId);
        if (po == null) {
            return null;
        }

        AssetAccountEntity entity = new AssetAccountEntity();
        entity.setId(po.getId());
        entity.setUserId(po.getUserId());
        entity.setTotalBalance(po.getTotalBalance());
        entity.setTotalSavings(po.getTotalSavings());
        entity.setIsDeleted(po.getIsDeleted());
        entity.setCreateTime(po.getCreateTime());
        entity.setUpdateTime(po.getUpdateTime());

        return entity;
    }
}
