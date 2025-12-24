package com.gaoyifeng.aioserver.infrastructure.adapter.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.IRecurringTransactionRepository;
import com.gaoyifeng.aioserver.domain.model.entity.RecurringTransactionEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.RecurringTransactionDao;
import com.gaoyifeng.aioserver.infrastructure.dao.po.RecurringTransactionPO;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 固定收支配置仓储实现
 */
@Repository
public class RecurringTransactionRepository implements IRecurringTransactionRepository {

    @Resource
    private RecurringTransactionDao recurringTransactionDao;

    @Override
    public void save(RecurringTransactionEntity entity) {
        RecurringTransactionPO po = new RecurringTransactionPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setTransactionType(entity.getTransactionType());
        po.setTransactionName(entity.getTransactionName());
        po.setAmount(entity.getAmount());
        po.setTriggerType(entity.getTriggerType());
        po.setTriggerValue(entity.getTriggerValue());
        po.setStatus(entity.getStatus());
        po.setEndDate(entity.getEndDate());
        recurringTransactionDao.insert(po);
    }

    @Override
    public void delete(String id, String userId) {
        recurringTransactionDao.softDelete(id, userId);
    }

    @Override
    public void update(RecurringTransactionEntity entity) {
        RecurringTransactionPO po = new RecurringTransactionPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setTransactionName(entity.getTransactionName());
        po.setAmount(entity.getAmount());
        po.setTriggerType(entity.getTriggerType());
        po.setTriggerValue(entity.getTriggerValue());
        po.setStatus(entity.getStatus());
        po.setEndDate(entity.getEndDate());
        recurringTransactionDao.update(po);
    }

    @Override
    public RecurringTransactionEntity queryById(String id) {
        RecurringTransactionPO po = recurringTransactionDao.queryById(id);
        if (po == null) {
            return null;
        }

        RecurringTransactionEntity entity = new RecurringTransactionEntity();
        entity.setId(po.getId());
        entity.setUserId(po.getUserId());
        entity.setTransactionType(po.getTransactionType());
        entity.setTransactionName(po.getTransactionName());
        entity.setAmount(po.getAmount());
        entity.setTriggerType(po.getTriggerType());
        entity.setTriggerValue(po.getTriggerValue());
        entity.setStatus(po.getStatus());
        entity.setEndDate(po.getEndDate());
        entity.setIsDeleted(po.getIsDeleted());
        entity.setCreateTime(po.getCreateTime());
        entity.setUpdateTime(po.getUpdateTime());

        return entity;
    }

    @Override
    public List<RecurringTransactionEntity> queryByUserId(String userId) {
        List<RecurringTransactionPO> poList = recurringTransactionDao.queryByUserId(userId);
        List<RecurringTransactionEntity> entityList = new ArrayList<>();

        for (RecurringTransactionPO po : poList) {
            RecurringTransactionEntity entity = new RecurringTransactionEntity();
            entity.setId(po.getId());
            entity.setUserId(po.getUserId());
            entity.setTransactionType(po.getTransactionType());
            entity.setTransactionName(po.getTransactionName());
            entity.setAmount(po.getAmount());
            entity.setTriggerType(po.getTriggerType());
            entity.setTriggerValue(po.getTriggerValue());
            entity.setStatus(po.getStatus());
            entity.setEndDate(po.getEndDate());
            entity.setIsDeleted(po.getIsDeleted());
            entity.setCreateTime(po.getCreateTime());
            entity.setUpdateTime(po.getUpdateTime());
            entityList.add(entity);
        }

        return entityList;
    }
}
