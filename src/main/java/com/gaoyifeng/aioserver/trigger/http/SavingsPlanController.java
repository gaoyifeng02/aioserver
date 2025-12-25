package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.ISavingsPlanService;
import com.gaoyifeng.aioserver.api.dto.asset.request.SavingsPlanAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.SavingsPlanUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.SavingsPlanResponseDto;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 存款计划控制器
 */
@Slf4j
@RestController
@RequestMapping("/asset/savings")
public class SavingsPlanController {

    @Resource
    private ISavingsPlanService savingsPlanService;

    /**
     * 新增存款计划
     */
    @PostMapping
    public Result<String> add(@RequestBody SavingsPlanAddRequestDto dto) {
        try {
            String id = savingsPlanService.add(dto);
            return Result.success(id);
        } catch (Exception e) {
            log.error("新增存款计划失败", e);
            return Result.fail(ResultCode._500.getCode(), "新增失败: " + e.getMessage());
        }
    }

    /**
     * 删除存款计划
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") String id) {
        try {
            savingsPlanService.delete(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除存款计划失败", e);
            return Result.fail(ResultCode._500.getCode(), "删除失败: " + e.getMessage());
        }
    }

    /**
     * 更新存款计划
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") String id, @RequestBody SavingsPlanUpdateRequestDto dto) {
        try {
            savingsPlanService.update(id, dto);
            return Result.success();
        } catch (Exception e) {
            log.error("更新存款计划失败", e);
            return Result.fail(ResultCode._500.getCode(), "更新失败: " + e.getMessage());
        }
    }

    /**
     * 查询单个存款计划
     */
    @GetMapping("/{id}")
    public Result<SavingsPlanResponseDto> getById(@PathVariable("id") String id) {
        try {
            SavingsPlanResponseDto vo = savingsPlanService.getById(id);
            if (vo == null) {
                return Result.fail(ResultCode._404.getCode(), "计划不存在");
            }
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询单个存款计划失败", e);
            return Result.fail(ResultCode._500.getCode(), "查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户的所有存款计划
     */
    @GetMapping("/list")
    public Result<List<SavingsPlanResponseDto>> list() {
        try {
            List<SavingsPlanResponseDto> voList = savingsPlanService.list();
            return Result.success(voList);
        } catch (Exception e) {
            log.error("查询存款计划列表失败", e);
            return Result.fail(ResultCode._500.getCode(), "查询失败: " + e.getMessage());
        }
    }
}
