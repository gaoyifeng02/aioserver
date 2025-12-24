package com.gaoyifeng.aioserver.infrastructure.adapter.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.ITransactionFlowRepository;
import com.gaoyifeng.aioserver.domain.model.entity.TransactionFlowEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.TransactionFlowDao;
import com.gaoyifeng.aioserver.infrastructure.dao.po.TransactionFlowPO;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 资产流水仓储实现
 */
@Repository
public class TransactionFlowRepository implements ITransactionFlowRepository {

    @Resource
    private TransactionFlowDao transactionFlowDao;

    @Override
    public TransactionFlowEntity queryById(String id) {
        TransactionFlowPO po = transactionFlowDao.queryById(id);
        if (po == null) {
            return null;
        }

        TransactionFlowEntity entity = new TransactionFlowEntity();
        entity.setId(po.getId());
        entity.setUserId(po.getUserId());
        entity.setFlowType(po.getFlowType());
        entity.setSourceId(po.getSourceId());
        entity.setTransactionType(po.getTransactionType());
        entity.setTransactionName(po.getTransactionName());
        entity.setAmount(po.getAmount());
        entity.setBalanceBefore(po.getBalanceBefore());
        entity.setBalanceAfter(po.getBalanceAfter());
        entity.setTransactionDatetime(po.getTransactionDatetime());
        entity.setDescription(po.getDescription());
        entity.setIsDeleted(po.getIsDeleted());
        entity.setCreateTime(po.getCreateTime());

        return entity;
    }

    @Override
    public List<TransactionFlowEntity> queryByUserId(String userId) {
        List<TransactionFlowPO> poList = transactionFlowDao.queryByUserId(userId);
        List<TransactionFlowEntity> entityList = new ArrayList<>();

        for (TransactionFlowPO po : poList) {
            TransactionFlowEntity entity = new TransactionFlowEntity();
            entity.setId(po.getId());
            entity.setUserId(po.getUserId());
            entity.setFlowType(po.getFlowType());
            entity.setSourceId(po.getSourceId());
            entity.setTransactionType(po.getTransactionType());
            entity.setTransactionName(po.getTransactionName());
            entity.setAmount(po.getAmount());
            entity.setBalanceBefore(po.getBalanceBefore());
            entity.setBalanceAfter(po.getBalanceAfter());
            entity.setTransactionDatetime(po.getTransactionDatetime());
            entity.setDescription(po.getDescription());
            entity.setIsDeleted(po.getIsDeleted());
            entity.setCreateTime(po.getCreateTime());
            entityList.add(entity);
        }

        return entityList;
    }
}
