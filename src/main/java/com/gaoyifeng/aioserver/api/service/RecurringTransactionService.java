package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.api.dto.asset.request.RecurringTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.RecurringTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.RecurringTransactionResponseDto;
import com.gaoyifeng.aioserver.api.service.IRecurringTransactionService;
import com.gaoyifeng.aioserver.domain.adapter.repository.IRecurringTransactionRepository;
import com.gaoyifeng.aioserver.domain.model.entity.RecurringTransactionEntity;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.infrastructure.util.SnowflakeIdWorker;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 固定收支配置服务实现
 */
@Service
public class RecurringTransactionService implements IRecurringTransactionService {

    @Resource
    private IRecurringTransactionRepository recurringTransactionRepository;

    @Override
    public String add(RecurringTransactionAddRequestDto dto) {
        // 获取当前登录用户ID
        String userId = LoginUserContext.getUserId();

        // 创建实体
        RecurringTransactionEntity entity = new RecurringTransactionEntity();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
        entity.setId(idWorker.nextIdStr());
        entity.setUserId(userId);
        entity.setTransactionType(dto.getTransactionType());
        entity.setTransactionName(dto.getTransactionName());
        entity.setAmount(dto.getAmount());
        entity.setTriggerType(dto.getTriggerType());
        entity.setTriggerValue(dto.getTriggerValue());
        entity.setStatus(dto.getStatus() != null ? dto.getStatus() : "ACTIVE");
        entity.setEndDate(dto.getEndDate());

        // 保存
        recurringTransactionRepository.save(entity);

        return entity.getId();
    }

    @Override
    public void delete(String id) {
        String userId = LoginUserContext.getUserId();
        recurringTransactionRepository.delete(id, userId);
    }

    @Override
    public void update(String id, RecurringTransactionUpdateRequestDto dto) {
        String userId = LoginUserContext.getUserId();

        // 查询原数据
        RecurringTransactionEntity existingEntity = recurringTransactionRepository.queryById(id);
        if (existingEntity == null) {
            throw new RuntimeException("配置不存在");
        }

        // 验证权限
        if (!existingEntity.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作此配置");
        }

        // 更新字段
        if (dto.getTransactionName() != null) {
            existingEntity.setTransactionName(dto.getTransactionName());
        }
        if (dto.getAmount() != null) {
            existingEntity.setAmount(dto.getAmount());
        }
        if (dto.getTriggerType() != null) {
            existingEntity.setTriggerType(dto.getTriggerType());
        }
        if (dto.getTriggerValue() != null) {
            existingEntity.setTriggerValue(dto.getTriggerValue());
        }
        if (dto.getStatus() != null) {
            existingEntity.setStatus(dto.getStatus());
        }
        if (dto.getEndDate() != null) {
            existingEntity.setEndDate(dto.getEndDate());
        }

        // 保存更新
        recurringTransactionRepository.update(existingEntity);
    }

    @Override
    public RecurringTransactionResponseDto getById(String id) {
        String userId = LoginUserContext.getUserId();
        RecurringTransactionEntity entity = recurringTransactionRepository.queryById(id);

        if (entity == null) {
            return null;
        }

        // 验证权限
        if (!entity.getUserId().equals(userId)) {
            throw new RuntimeException("无权查看此配置");
        }

        // 转换为VO
        return convertToVO(entity);
    }

    @Override
    public List<RecurringTransactionResponseDto> list() {
        String userId = LoginUserContext.getUserId();
        List<RecurringTransactionEntity> entityList = recurringTransactionRepository.queryByUserId(userId);

        List<RecurringTransactionResponseDto> voList = new ArrayList<>();
        for (RecurringTransactionEntity entity : entityList) {
            voList.add(convertToVO(entity));
        }

        return voList;
    }

    /**
     * 转换Entity为VO
     */
    private RecurringTransactionResponseDto convertToVO(RecurringTransactionEntity entity) {
        RecurringTransactionResponseDto vo = new RecurringTransactionResponseDto();
        vo.setId(entity.getId());
        vo.setUserId(entity.getUserId());
        vo.setTransactionType(entity.getTransactionType());
        vo.setTransactionName(entity.getTransactionName());
        vo.setAmount(entity.getAmount());
        vo.setTriggerType(entity.getTriggerType());
        vo.setTriggerValue(entity.getTriggerValue());
        vo.setStatus(entity.getStatus());
        vo.setEndDate(entity.getEndDate());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());
        return vo;
    }
}
