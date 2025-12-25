package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.api.dto.asset.request.SavingsPlanAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.SavingsPlanUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.SavingsPlanResponseDto;
import com.gaoyifeng.aioserver.api.ISavingsPlanService;
import com.gaoyifeng.aioserver.domain.adapter.repository.ISavingsPlanRepository;
import com.gaoyifeng.aioserver.domain.model.entity.SavingsPlanEntity;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.infrastructure.util.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 存款计划服务实现
 */
@Service
public class SavingsPlanService implements ISavingsPlanService {

    @Resource
    private ISavingsPlanRepository savingsPlanRepository;

    @Override
    public String add(SavingsPlanAddRequestDto dto) {
        String userId = LoginUserContext.getUserId();

        SavingsPlanEntity entity = new SavingsPlanEntity();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
        entity.setId(idWorker.nextIdStr());
        entity.setUserId(userId);
        entity.setPlanName(dto.getPlanName());
        entity.setStartDate(dto.getStartDate() != null ? dto.getStartDate() : LocalDate.now());
        entity.setMonthlyDepositAmount(dto.getMonthlyDepositAmount());
        entity.setInterestCalculationType(dto.getInterestCalculationType());
        entity.setInterestRate(dto.getInterestRate());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        entity.setDescription(dto.getDescription());

        savingsPlanRepository.save(entity);
        return entity.getId();
    }

    @Override
    public void delete(String id) {
        String userId = LoginUserContext.getUserId();
        savingsPlanRepository.delete(id, userId);
    }

    @Override
    public void update(String id, SavingsPlanUpdateRequestDto dto) {
        String userId = LoginUserContext.getUserId();

        SavingsPlanEntity existingEntity = savingsPlanRepository.queryById(id);
        if (existingEntity == null) {
            throw new RuntimeException("计划不存在");
        }

        if (!existingEntity.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作此计划");
        }

        if (dto.getPlanName() != null) {
            existingEntity.setPlanName(dto.getPlanName());
        }
        if (dto.getMonthlyDepositAmount() != null) {
            existingEntity.setMonthlyDepositAmount(dto.getMonthlyDepositAmount());
        }
        if (dto.getInterestCalculationType() != null) {
            existingEntity.setInterestCalculationType(dto.getInterestCalculationType());
        }
        if (dto.getInterestRate() != null) {
            existingEntity.setInterestRate(dto.getInterestRate());
        }
        if (dto.getStatus() != null) {
            existingEntity.setStatus(dto.getStatus());
        }
        if (dto.getDescription() != null) {
            existingEntity.setDescription(dto.getDescription());
        }

        savingsPlanRepository.update(existingEntity);
    }

    @Override
    public SavingsPlanResponseDto getById(String id) {
        String userId = LoginUserContext.getUserId();
        SavingsPlanEntity entity = savingsPlanRepository.queryById(id);

        if (entity == null) {
            return null;
        }

        if (!entity.getUserId().equals(userId)) {
            throw new RuntimeException("无权查看此计划");
        }

        return convertToVO(entity);
    }

    @Override
    public List<SavingsPlanResponseDto> list() {
        String userId = LoginUserContext.getUserId();
        List<SavingsPlanEntity> entityList = savingsPlanRepository.queryByUserId(userId);

        List<SavingsPlanResponseDto> voList = new ArrayList<>();
        for (SavingsPlanEntity entity : entityList) {
            voList.add(convertToVO(entity));
        }

        return voList;
    }

    private SavingsPlanResponseDto convertToVO(SavingsPlanEntity entity) {
        SavingsPlanResponseDto vo = new SavingsPlanResponseDto();
        vo.setId(entity.getId());
        vo.setUserId(entity.getUserId());
        vo.setPlanName(entity.getPlanName());
        vo.setStartDate(entity.getStartDate());
        vo.setMonthlyDepositAmount(entity.getMonthlyDepositAmount());
        vo.setInterestCalculationType(entity.getInterestCalculationType());
        vo.setInterestRate(entity.getInterestRate());
        vo.setStatus(entity.getStatus());
        vo.setDescription(entity.getDescription());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
}
