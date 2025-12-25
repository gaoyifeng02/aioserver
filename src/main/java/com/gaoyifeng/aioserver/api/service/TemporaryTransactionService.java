package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.domain.adapter.repository.ITemporaryTransactionRepository;
import com.gaoyifeng.aioserver.domain.model.entity.TemporaryTransactionEntity;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.infrastructure.util.SnowflakeIdWorker;
import com.gaoyifeng.aioserver.types.dto.TemporaryTransactionAddDTO;
import com.gaoyifeng.aioserver.types.dto.TemporaryTransactionUpdateDTO;
import com.gaoyifeng.aioserver.types.vo.TemporaryTransactionVO;
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
    public void add(String userId, TemporaryTransactionAddDTO dto) {
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
    public void update(String id, String userId, TemporaryTransactionUpdateDTO dto) {
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
    public TemporaryTransactionVO queryById(String id) {
        TemporaryTransactionEntity entity = temporaryTransactionRepository.queryById(id);
        if (entity == null) {
            throw new RuntimeException("临时收支记录不存在");
        }

        TemporaryTransactionVO vo = new TemporaryTransactionVO();
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
    public List<TemporaryTransactionVO> queryByUserId(String userId) {
        List<TemporaryTransactionEntity> entityList = temporaryTransactionRepository.queryByUserId(userId);
        List<TemporaryTransactionVO> voList = new ArrayList<>();

        for (TemporaryTransactionEntity entity : entityList) {
            TemporaryTransactionVO vo = new TemporaryTransactionVO();
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
