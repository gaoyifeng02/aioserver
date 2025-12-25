package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.asset.request.SavingsPlanAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.SavingsPlanUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.SavingsPlanResponseDto;

import java.util.List;

/**
 * 存款计划服务接口
 */
public interface ISavingsPlanService {

    /**
     * 新增存款计划
     * @param dto 新增请求DTO
     * @return 计划ID
     */
    String add(SavingsPlanAddRequestDto dto);

    /**
     * 删除存款计划
     * @param id 计划ID
     */
    void delete(String id);

    /**
     * 更新存款计划
     * @param id 计划ID
     * @param dto 更新请求DTO
     */
    void update(String id, SavingsPlanUpdateRequestDto dto);

    /**
     * 查询单个存款计划
     * @param id 计划ID
     * @return 存款计划VO
     */
    SavingsPlanResponseDto getById(String id);

    /**
     * 查询用户的所有存款计划
     * @return 存款计划VO列表
     */
    List<SavingsPlanResponseDto> list();
}
