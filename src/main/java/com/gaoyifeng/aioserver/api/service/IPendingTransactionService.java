package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.domain.model.entity.PendingTransactionEntity;
import com.gaoyifeng.aioserver.types.dto.PendingTransactionAddDTO;
import com.gaoyifeng.aioserver.types.dto.PendingTransactionUpdateDTO;
import com.gaoyifeng.aioserver.types.vo.PendingTransactionVO;

import java.util.List;

/**
 * 待入账服务接口
 */
public interface IPendingTransactionService {

    /**
     * 添加待入账
     */
    void add(String userId, PendingTransactionAddDTO dto);

    /**
     * 删除待入账
     */
    void delete(String id, String userId);

    /**
     * 更新待入账
     */
    void update(String id, String userId, PendingTransactionUpdateDTO dto);

    /**
     * 根据ID查询待入账
     */
    PendingTransactionVO queryById(String id);

    /**
     * 查询用户所有待入账
     */
    List<PendingTransactionVO> queryByUserId(String userId);
}
