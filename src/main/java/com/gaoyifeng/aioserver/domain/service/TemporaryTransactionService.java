package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.api.ITemporaryTransactionService;
import com.gaoyifeng.aioserver.api.dto.asset.request.TemporaryTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.TemporaryTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.TemporaryTransactionResponseDto;
import com.gaoyifeng.aioserver.domain.adapter.repository.ITemporaryTransactionRepository;
import com.gaoyifeng.aioserver.domain.model.entity.TemporaryTransactionEntity;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.infrastructure.util.SnowflakeIdWorker;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 临时收支记录服务实现
 */
@Service
public class TemporaryTransactionService implements ITemporaryTransactionService {

    @Resource
    private ITemporaryTransactionRepository temporaryTransactionRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(String userId, TemporaryTransactionAddRequestDto dto) {
        TemporaryTransactionEntity entity = new TemporaryTransactionEntity();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(1, 1);
        entity.setId(idWorker.nextIdStr());
        entity.setUserId(userId);
        entity.setTransactionType(dto.getTransactionType());
        entity.setTransactionName(dto.getTransactionName());
        entity.setTransactionDatetime(dto.getTransactionDatetime() != null ? dto.getTransactionDatetime() : LocalDateTime.now());
        entity.setDescription(dto.getDescription());

        temporaryTransactionRepository.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id, String userId) {
        TemporaryTransactionEntity entity = temporaryTransactionRepository.queryById(id);
        if (entity == null) {
            throw new RuntimeException("临时收支记录不存在");
        }
        temporaryTransactionRepository.delete(id, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(String id, String userId, TemporaryTransactionUpdateRequestDto dto) {
        TemporaryTransactionEntity entity = temporaryTransactionRepository.queryById(id);
        if (entity == null) {
            throw new RuntimeException("临时收支记录不存在");
        }

        entity.setId(id);
        entity.setUserId(userId);
        entity.setTransactionName(dto.getTransactionName());
        entity.setTransactionDatetime(dto.getTransactionDatetime());
        entity.setDescription(dto.getDescription());

        temporaryTransactionRepository.update(entity);
    }

    @Override
    public TemporaryTransactionResponseDto queryById(String id) {
        TemporaryTransactionEntity entity = temporaryTransactionRepository.queryById(id);
        if (entity == null) {
            throw new RuntimeException("临时收支记录不存在");
        }

        TemporaryTransactionResponseDto vo = new TemporaryTransactionResponseDto();
        vo.setId(entity.getId());
        vo.setUserId(entity.getUserId());
        vo.setTransactionType(entity.getTransactionType());
        vo.setTransactionName(entity.getTransactionName());
        vo.setTransactionDatetime(entity.getTransactionDatetime());
        vo.setDescription(entity.getDescription());
        vo.setCreateTime(entity.getCreateTime());
        vo.setUpdateTime(entity.getUpdateTime());

        return vo;
    }

    @Override
    public List<TemporaryTransactionResponseDto> queryByUserId(String userId) {
        List<TemporaryTransactionEntity> entityList = temporaryTransactionRepository.queryByUserId(userId);
        List<TemporaryTransactionResponseDto> voList = new ArrayList<>();

        for (TemporaryTransactionEntity entity : entityList) {
            TemporaryTransactionResponseDto vo = new TemporaryTransactionResponseDto();
            vo.setId(entity.getId());
            vo.setUserId(entity.getUserId());
            vo.setTransactionType(entity.getTransactionType());
            vo.setTransactionName(entity.getTransactionName());
            vo.setTransactionDatetime(entity.getTransactionDatetime());
            vo.setDescription(entity.getDescription());
            vo.setCreateTime(entity.getCreateTime());
            vo.setUpdateTime(entity.getUpdateTime());

            voList.add(vo);
        }

        return voList;
    }
}
