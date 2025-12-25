package com.gaoyifeng.aioserver.api.service;

import com.gaoyifeng.aioserver.api.dto.asset.request.RecurringTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.RecurringTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.RecurringTransactionResponseDto;

import java.util.List;

/**
 * 固定收支配置服务接口
 */
public interface IRecurringTransactionService {

    /**
     * 新增固定收支配置
     * @param dto 新增请求DTO
     * @return 配置ID
     */
    String add(RecurringTransactionAddRequestDto dto);

    /**
     * 删除固定收支配置
     * @param id 配置ID
     */
    void delete(String id);

    /**
     * 更新固定收支配置
     * @param id 配置ID
     * @param dto 更新请求DTO
     */
    void update(String id, RecurringTransactionUpdateRequestDto dto);

    /**
     * 查询单个固定收支配置
     * @param id 配置ID
     * @return 固定收支配置VO
     */
    RecurringTransactionResponseDto getById(String id);

    /**
     * 查询用户的所有固定收支配置
     * @return 固定收支配置VO列表
     */
    List<RecurringTransactionResponseDto> list();
}
