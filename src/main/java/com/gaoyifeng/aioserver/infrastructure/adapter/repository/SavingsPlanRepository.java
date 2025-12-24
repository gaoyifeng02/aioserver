package com.gaoyifeng.aioserver.infrastructure.adapter.repository;

import com.gaoyifeng.aioserver.domain.adapter.repository.ISavingsPlanRepository;
import com.gaoyifeng.aioserver.domain.model.entity.SavingsPlanEntity;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.SavingsPlanDao;
import com.gaoyifeng.aioserver.infrastructure.dao.po.SavingsPlanPO;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 存款计划仓储实现
 */
@Repository
public class SavingsPlanRepository implements ISavingsPlanRepository {

    @Resource
    private SavingsPlanDao savingsPlanDao;

    @Override
    public void save(SavingsPlanEntity entity) {
        SavingsPlanPO po = new SavingsPlanPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setPlanName(entity.getPlanName());
        po.setStartDate(entity.getStartDate());
        po.setMonthlyDepositAmount(entity.getMonthlyDepositAmount());
        po.setInterestCalculationType(entity.getInterestCalculationType());
        po.setInterestRate(entity.getInterestRate());
        po.setStatus(entity.getStatus());
        po.setDescription(entity.getDescription());
        savingsPlanDao.insert(po);
    }

    @Override
    public void delete(String id, String userId) {
        savingsPlanDao.softDelete(id, userId);
    }

    @Override
    public void update(SavingsPlanEntity entity) {
        SavingsPlanPO po = new SavingsPlanPO();
        po.setId(entity.getId());
        po.setUserId(entity.getUserId());
        po.setPlanName(entity.getPlanName());
        po.setMonthlyDepositAmount(entity.getMonthlyDepositAmount());
        po.setInterestCalculationType(entity.getInterestCalculationType());
        po.setInterestRate(entity.getInterestRate());
        po.setStatus(entity.getStatus());
        po.setDescription(entity.getDescription());
        savingsPlanDao.update(po);
    }

    @Override
    public SavingsPlanEntity queryById(String id) {
        SavingsPlanPO po = savingsPlanDao.queryById(id);
        if (po == null) {
            return null;
        }

        SavingsPlanEntity entity = new SavingsPlanEntity();
        entity.setId(po.getId());
        entity.setUserId(po.getUserId());
        entity.setPlanName(po.getPlanName());
        entity.setStartDate(po.getStartDate());
        entity.setMonthlyDepositAmount(po.getMonthlyDepositAmount());
        entity.setInterestCalculationType(po.getInterestCalculationType());
        entity.setInterestRate(po.getInterestRate());
        entity.setStatus(po.getStatus());
        entity.setDescription(po.getDescription());
        entity.setIsDeleted(po.getIsDeleted());
        entity.setCreateTime(po.getCreateTime());
        entity.setUpdateTime(po.getUpdateTime());

        return entity;
    }

    @Override
    public List<SavingsPlanEntity> queryByUserId(String userId) {
        List<SavingsPlanPO> poList = savingsPlanDao.queryByUserId(userId);
        List<SavingsPlanEntity> entityList = new ArrayList<>();

        for (SavingsPlanPO po : poList) {
            SavingsPlanEntity entity = new SavingsPlanEntity();
            entity.setId(po.getId());
            entity.setUserId(po.getUserId());
            entity.setPlanName(po.getPlanName());
            entity.setStartDate(po.getStartDate());
            entity.setMonthlyDepositAmount(po.getMonthlyDepositAmount());
            entity.setInterestCalculationType(po.getInterestCalculationType());
            entity.setInterestRate(po.getInterestRate());
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
