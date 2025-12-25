package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.dto.asset.response.TransactionFlowResponseDto;
import com.gaoyifeng.aioserver.api.service.ITransactionFlowService;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 资产流水控制器
 */
@Slf4j
@RestController
@RequestMapping("/asset/flow")
public class TransactionFlowController {

    @Resource
    private ITransactionFlowService transactionFlowService;

    /**
     * 查询单个资产流水
     */
    @GetMapping("/{id}")
    public Result<TransactionFlowResponseDto> getById(@PathVariable("id") String id) {
        try {
            TransactionFlowResponseDto vo = transactionFlowService.getById(id);
            if (vo == null) {
                return Result.fail(ResultCode._404.getCode(), "流水不存在");
            }
            return Result.success(vo);
        } catch (Exception e) {
            log.error("查询单个资产流水失败", e);
            return Result.fail(ResultCode._500.getCode(), "查询失败: " + e.getMessage());
        }
    }

    /**
     * 查询用户的所有资产流水
     */
    @GetMapping("/list")
    public Result<List<TransactionFlowResponseDto>> list() {
        try {
            List<TransactionFlowResponseDto> voList = transactionFlowService.list();
            return Result.success(voList);
        } catch (Exception e) {
            log.error("查询资产流水列表失败", e);
            return Result.fail(ResultCode._500.getCode(), "查询失败: " + e.getMessage());
        }
    }
}
