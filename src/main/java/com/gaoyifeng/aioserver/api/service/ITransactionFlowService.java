package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.api.dto.asset.response.TransactionFlowResponseDto;

import java.util.List;

/**
 * 资产流水服务接口
 */
public interface ITransactionFlowService {

    /**
     * 查询单个资产流水
     * @param id 流水ID
     * @return 资产流水VO
     */
    TransactionFlowResponseDto getById(String id);

    /**
     * 查询用户的所有资产流水
     * @return 资产流水VO列表
     */
    List<TransactionFlowResponseDto> list();
}
