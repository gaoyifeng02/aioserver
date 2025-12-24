package com.gaoyifeng.aioserver.infrastructure.adapter.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.IPendingTransactionRepository;
import com.gaoyifeng.aioserver.domain.model.entity.PendingTransactionEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.PendingTransactionDao;
import com.gaoyifeng.aioserver.infrastructure.dao.po.PendingTransactionPO;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PendingTransactionRepository implements IPendingTransactionRepository {

    @Resource
    private PendingTransactionDao pendingTransactionDao;

    @Override
    public void save(PendingTransactionEntity entity) {
        PendingTransactionPO po = new PendingTransactionPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setTransactionType(entity.getTransactionType());
        po.setTransactionName(entity.getTransactionName());
        po.setTotalAmount(entity.getTotalAmount());
        po.setRemainingAmount(entity.getRemainingAmount());
        po.setStatus(entity.getStatus());
        po.setDescription(entity.getDescription());
        pendingTransactionDao.insert(po);
    }

    @Override
    public void delete(String id, String userId) {
        pendingTransactionDao.softDelete(id, userId);
    }

    @Override
    public void update(PendingTransactionEntity entity) {
        PendingTransactionPO po = new PendingTransactionPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setTransactionName(entity.getTransactionName());
        po.setRemainingAmount(entity.getRemainingAmount());
        po.setStatus(entity.getStatus());
        po.setDescription(entity.getDescription());
        pendingTransactionDao.update(po);
    }

    @Override
    public PendingTransactionEntity queryById(String id) {
        PendingTransactionPO po = pendingTransactionDao.queryById(id);
        if (po == null) return null;

        PendingTransactionEntity entity = new PendingTransactionEntity();
        entity.setId(po.getId());
        entity.setUserId(po.getUserId());
        entity.setTransactionType(po.getTransactionType());
        entity.setTransactionName(po.getTransactionName());
        entity.setTotalAmount(po.getTotalAmount());
        entity.setRemainingAmount(po.getRemainingAmount());
        entity.setStatus(po.getStatus());
        entity.setDescription(po.getDescription());
        entity.setIsDeleted(po.getIsDeleted());
        entity.setCreateTime(po.getCreateTime());
        entity.setUpdateTime(po.getUpdateTime());
        return entity;
    }

    @Override
    public List<PendingTransactionEntity> queryByUserId(String userId) {
        List<PendingTransactionPO> poList = pendingTransactionDao.queryByUserId(userId);
        List<PendingTransactionEntity> entityList = new ArrayList<>();

        for (PendingTransactionPO po : poList) {
            PendingTransactionEntity entity = new PendingTransactionEntity();
            entity.setId(po.getId());
            entity.setUserId(po.getUserId());
            entity.setTransactionType(po.getTransactionType());
            entity.setTransactionName(po.getTransactionName());
            entity.setTotalAmount(po.getTotalAmount());
            entity.setRemainingAmount(po.getRemainingAmount());
            entity.setStatus(po.getStatus());
            entity.setDescription(po.getDescription());
            entity.setIsDeleted(po.getIsDeleted());
            entity.setCreateTime(po.getCreateTime());
            entity.setUpdateTime(po.getUpdateTime());
            entityList.add(entity);
        }

        return entityList;
    }
}
