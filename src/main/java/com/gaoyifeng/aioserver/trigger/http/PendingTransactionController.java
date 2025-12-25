package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.IPendingTransactionService;
import com.gaoyifeng.aioserver.api.dto.asset.request.PendingTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.PendingTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.PendingTransactionResponseDto;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.types.common.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 待入账控制器
 */
@RestController
@RequestMapping("/asset/pending")
public class PendingTransactionController {

    @Resource
    private IPendingTransactionService pendingTransactionService;

    /**
     * 添加待入账
     */
    @PostMapping
    public Result<String> add(@RequestBody PendingTransactionAddRequestDto dto) {
        String userId = LoginUserContext.getUserId();
        pendingTransactionService.add(userId, dto);
        return Result.success("添加成功");
    }

    /**
     * 删除待入账
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        String userId = LoginUserContext.getUserId();
        pendingTransactionService.delete(id, userId);
        return Result.success("删除成功");
    }

    /**
     * 更新待入账
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody PendingTransactionUpdateRequestDto dto) {
        String userId = LoginUserContext.getUserId();
        pendingTransactionService.update(id, userId, dto);
        return Result.success("更新成功");
    }

    /**
     * 根据ID查询待入账
     */
    @GetMapping("/{id}")
    public Result<PendingTransactionResponseDto> queryById(@PathVariable String id) {
        PendingTransactionResponseDto vo = pendingTransactionService.queryById(id);
        return Result.success(vo);
    }

    /**
     * 查询用户所有待入账
     */
    @GetMapping("/list")
    public Result<List<PendingTransactionResponseDto>> queryByUserId() {
        String userId = LoginUserContext.getUserId();
        List<PendingTransactionResponseDto> voList = pendingTransactionService.queryByUserId(userId);
        return Result.success(voList);
    }
}
