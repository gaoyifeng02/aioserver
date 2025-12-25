package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.api.dto.asset.request.TemporaryTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.TemporaryTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.TemporaryTransactionResponseDto;
import com.gaoyifeng.aioserver.domain.model.entity.TemporaryTransactionEntity;

import java.util.List;

/**
 * 临时收支记录服务接口
 */
public interface ITemporaryTransactionService {

    /**
     * 添加临时收支记录
     */
    void add(String userId, TemporaryTransactionAddRequestDto dto);

    /**
     * 删除临时收支记录
     */
    void delete(String id, String userId);

    /**
     * 更新临时收支记录
     */
    void update(String id, String userId, TemporaryTransactionUpdateRequestDto dto);

    /**
     * 根据ID查询临时收支记录
     */
    TemporaryTransactionResponseDto queryById(String id);

    /**
     * 查询用户所有临时收支记录
     */
    List<TemporaryTransactionResponseDto> queryByUserId(String userId);
}
