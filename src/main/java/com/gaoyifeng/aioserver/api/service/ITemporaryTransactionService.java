package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.domain.model.entity.TemporaryTransactionEntity;
import com.gaoyifeng.aioserver.types.dto.TemporaryTransactionAddDTO;
import com.gaoyifeng.aioserver.types.dto.TemporaryTransactionUpdateDTO;
import com.gaoyifeng.aioserver.types.vo.TemporaryTransactionVO;

import java.util.List;

/**
 * 临时收支记录服务接口
 */
public interface ITemporaryTransactionService {

    /**
     * 添加临时收支记录
     */
    void add(String userId, TemporaryTransactionAddDTO dto);

    /**
     * 删除临时收支记录
     */
    void delete(String id, String userId);

    /**
     * 更新临时收支记录
     */
    void update(String id, String userId, TemporaryTransactionUpdateDTO dto);

    /**
     * 根据ID查询临时收支记录
     */
    TemporaryTransactionVO queryById(String id);

    /**
     * 查询用户所有临时收支记录
     */
    List<TemporaryTransactionVO> queryByUserId(String userId);
}
