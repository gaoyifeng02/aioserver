package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.service.IRecurringTransactionService;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import com.gaoyifeng.aioserver.types.dto.RecurringTransactionAddDTO;
import com.gaoyifeng.aioserver.types.dto.RecurringTransactionUpdateDTO;
import com.gaoyifeng.aioserver.types.vo.RecurringTransactionVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 固定收支配置控制器
 */
@Slf4j
@RestController
@RequestMapping("/asset/recurring")
public class RecurringTransactionController {

    @Resource
    private IRecurringTransactionService recurringTransactionService;

    /**
     * 新增固定收支配置
     * @param dto 新增请求DTO
     * @return 配置ID
     */
    @PostMapping
    public Result<String> add(@RequestBody RecurringTransactionAddDTO dto) {
        try {
            String id = recurringTransactionService.add(dto);
            return Result.success(id);
        } catch (Exception e) {
            log.error("新增固定收支配置失败", e);
            return Result.fail(ResultCode._500.getCode(), "新增失败: " + e.getMessage());
        }
    }

    /**
     * 删除固定收支配置
     * @param id 配置ID
     * @return 成功/失败
     */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable("id") String id) {
        try {
            recurringTransactionService.delete(id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除固定收支配置失败", e);
            return Result.fail(ResultCode._500.getCode(), "删除失败: " + e.getMessage());
        }
    }

    /**
     * 更新固定收支配置
     * @param id 配置ID
     * @param dto 更新请求DTO
     * @return 成功/失败
     */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable("id") String id, @RequestBody RecurringTransactionUpdateDTO dto) {
        try {
            recurringTransactionService.update(id, dto);
            return Result.success();
        } catch (Exception e) {
            log.error("更新固定收支配置失败", e);
            return Result.fail(ResultCode._500.getCode(), "更新失败: " + e.getMessage());
        }
    }

    /**
     * 查询单个固定收支配置
     * @param id 配置ID
     * @return 固定收支配置VO
     */
    @GetMapping("/{id}")
    public Result<RecurringTransactionVO> getById(@PathVariable("id") String id) {
        try {
            RecurringTransactionVO vo = recurringTransactionService.getById(id);
            if (vo == null) {
                return Result.fail(ResultCode._404.getCode(), "配置不存在");
            }
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询单个固定收支配置失败", e);
            return Result.fail(ResultCode._500.getCode(), "查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户的所有固定收支配置
     * @return 固定收支配置VO列表
     */
    @GetMapping("/list")
    public Result<List<RecurringTransactionVO>> list() {
        try {
            List<RecurringTransactionVO> voList = recurringTransactionService.list();
            return Result.success(voList);
        } catch (Exception e) {
            log.error("查询固定收支配置列表失败", e);
            return Result.fail(ResultCode._500.getCode(), "查询失败: " + e.getMessage());
        }
    }
}
