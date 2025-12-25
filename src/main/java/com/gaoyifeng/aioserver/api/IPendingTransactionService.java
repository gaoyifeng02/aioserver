package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.asset.request.PendingTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.PendingTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.PendingTransactionResponseDto;
import com.gaoyifeng.aioserver.domain.model.entity.PendingTransactionEntity;

import java.util.List;

/**
 * 待入账服务接口
 */
public interface IPendingTransactionService {

    /**
     * 添加待入账
     */
    void add(String userId, PendingTransactionAddRequestDto dto);

    /**
     * 删除待入账
     */
    void delete(String id, String userId);

    /**
     * 更新待入账
     */
    void update(String id, String userId, PendingTransactionUpdateRequestDto dto);

    /**
     * 根据ID查询待入账
     */
    PendingTransactionResponseDto queryById(String id);

    /**
     * 查询用户所有待入账
     */
    List<PendingTransactionResponseDto> queryByUserId(String userId);
}
