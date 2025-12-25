package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.api.IPendingTransactionService;
import com.gaoyifeng.aioserver.api.dto.asset.request.PendingTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.PendingTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.PendingTransactionResponseDto;
import com.gaoyifeng.aioserver.domain.adapter.repository.IPendingTransactionRepository;
import com.gaoyifeng.aioserver.domain.model.entity.PendingTransactionEntity;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.infrastructure.util.SnowflakeIdWorker;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 待入账服务实现
 */
@Service
public class PendingTransactionService implements IPendingTransactionService {

    @Resource
    private IPendingTransactionRepository pendingTransactionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(String userId, PendingTransactionAddRequestDto dto) {
        PendingTransactionEntity entity = new PendingTransactionEntity();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
        entity.setId(idWorker.nextIdStr());
        entity.setUserId(userId);
        entity.setTransactionType(dto.getTransactionType());
        entity.setTransactionName(dto.getTransactionName());
        entity.setTotalAmount(dto.getTotalAmount());
        entity.setRemainingAmount(dto.getTotalAmount());
        entity.setStatus("PENDING");
        entity.setDescription(dto.getDescription());

        pendingTransactionRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id, String userId) {
        PendingTransactionEntity entity = pendingTransactionRepository.queryById(id);
        if (entity == null) {
            throw new RuntimeException("待入账记录不存在");
        }
        pendingTransactionRepository.delete(id, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String id, String userId, PendingTransactionUpdateRequestDto dto) {
        PendingTransactionEntity entity = pendingTransactionRepository.queryById(id);
        if (entity == null) {
            throw new RuntimeException("待入账记录不存在");
        }

        entity.setId(id);
        entity.setUserId(userId);
        entity.setTransactionName(dto.getTransactionName());
        entity.setRemainingAmount(dto.getRemainingAmount());
        entity.setStatus(dto.getStatus());
        entity.setDescription(dto.getDescription());

        pendingTransactionRepository.update(entity);
    }

    @Override
    public PendingTransactionResponseDto queryById(String id) {
        PendingTransactionEntity entity = pendingTransactionRepository.queryById(id);
        if (entity == null) {
            throw new RuntimeException("待入账记录不存在");
        }

        PendingTransactionResponseDto vo = new PendingTransactionResponseDto();
        vo.setId(entity.getId());
        vo.setUserId(entity.getUserId());
        vo.setTransactionType(entity.getTransactionType());
        vo.setTransactionName(entity.getTransactionName());
        vo.setTotalAmount(entity.getTotalAmount());
        vo.setRemainingAmount(entity.getRemainingAmount());
        vo.setStatus(entity.getStatus());
        vo.setDescription(entity.getDescription());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());

        return vo;
    }

    @Override
    public List<PendingTransactionResponseDto> queryByUserId(String userId) {
        List<PendingTransactionEntity> entityList = pendingTransactionRepository.queryByUserId(userId);
        List<PendingTransactionResponseDto> voList = new ArrayList<>();

        for (PendingTransactionEntity entity : entityList) {
            PendingTransactionResponseDto vo = new PendingTransactionResponseDto();
            vo.setId(entity.getId());
            vo.setUserId(entity.getUserId());
            vo.setTransactionType(entity.getTransactionType());
            vo.setTransactionName(entity.getTransactionName());
            vo.setTotalAmount(entity.getTotalAmount());
            vo.setRemainingAmount(entity.getRemainingAmount());
            vo.setStatus(entity.getStatus());
            vo.setDescription(entity.getDescription());
            vo.setCreateTime(entity.getCreateTime());
            vo.setUpdateTime(entity.getUpdateTime());

            voList.add(vo);
        }

        return voList;
    }
}
