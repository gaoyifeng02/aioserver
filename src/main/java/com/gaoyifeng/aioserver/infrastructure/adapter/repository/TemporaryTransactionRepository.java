package com.gaoyifeng.aioserver.infrastructure.adapter.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.ITemporaryTransactionRepository;
import com.gaoyifeng.aioserver.domain.model.entity.TemporaryTransactionEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.TemporaryTransactionDao;
import com.gaoyifeng.aioserver.infrastructure.dao.po.TemporaryTransactionPO;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TemporaryTransactionRepository implements ITemporaryTransactionRepository {

    @Resource
    private TemporaryTransactionDao temporaryTransactionDao;

    @Override
    public void save(TemporaryTransactionEntity entity) {
        TemporaryTransactionPO po = new TemporaryTransactionPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setTransactionType(entity.getTransactionType());
        po.setTransactionName(entity.getTransactionName());
        po.setTransactionDatetime(entity.getTransactionDatetime());
        po.setDescription(entity.getDescription());
        temporaryTransactionDao.insert(po);
    }

    @Override
    public void delete(String id, String userId) {
        temporaryTransactionDao.softDelete(id, userId);
    }

    @Override
    public void update(TemporaryTransactionEntity entity) {
        TemporaryTransactionPO po = new TemporaryTransactionPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setTransactionName(entity.getTransactionName());
        po.setTransactionDatetime(entity.getTransactionDatetime());
        po.setDescription(entity.getDescription());
        temporaryTransactionDao.update(po);
    }

    @Override
    public TemporaryTransactionEntity queryById(String id) {
        TemporaryTransactionPO po = temporaryTransactionDao.queryById(id);
        if (po == null) return null;

        TemporaryTransactionEntity entity = new TemporaryTransactionEntity();
        entity.setId(po.getId());
        entity.setUserId(po.getUserId());
        entity.setTransactionType(po.getTransactionType());
        entity.setTransactionName(po.getTransactionName());
        entity.setTransactionDatetime(po.getTransactionDatetime());
        entity.setDescription(po.getDescription());
        entity.setIsDeleted(po.getIsDeleted());
        entity.setCreateTime(po.getCreateTime());
        entity.setUpdateTime(po.getUpdateTime());
        return entity;
    }

    @Override
    public List<TemporaryTransactionEntity> queryByUserId(String userId) {
        List<TemporaryTransactionPO> poList = temporaryTransactionDao.queryByUserId(userId);
        List<TemporaryTransactionEntity> entityList = new ArrayList<>();

        for (TemporaryTransactionPO po : poList) {
            TemporaryTransactionEntity entity = new TemporaryTransactionEntity();
            entity.setId(po.getId());
            entity.setUserId(po.getUserId());
            entity.setTransactionType(po.getTransactionType());
            entity.setTransactionName(po.getTransactionName());
            entity.setTransactionDatetime(po.getTransactionDatetime());
            entity.setDescription(po.getDescription());
            entity.setIsDeleted(po.getIsDeleted());
            entity.setCreateTime(po.getCreateTime());
            entity.setUpdateTime(po.getUpdateTime());
            entityList.add(entity);
        }

        return entityList;
    }
}
