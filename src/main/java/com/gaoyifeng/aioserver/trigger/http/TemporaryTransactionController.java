package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.ITemporaryTransactionService;
import com.gaoyifeng.aioserver.api.dto.asset.request.TemporaryTransactionAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.request.TemporaryTransactionUpdateRequestDto;
import com.gaoyifeng.aioserver.api.dto.asset.response.TemporaryTransactionResponseDto;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.types.common.Result;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 临时收支记录控制器
 */
@RestController
@RequestMapping("/asset/temporary")
public class TemporaryTransactionController {

    @Resource
    private ITemporaryTransactionService temporaryTransactionService;

    /**
     * 添加临时收支记录
     */
    @PostMapping
    public Result<String> add(@RequestBody TemporaryTransactionAddRequestDto dto) {
        String userId = LoginUserContext.getUserId();
        temporaryTransactionService.add(userId, dto);
        return Result.success("添加成功");
    }

    /**
     * 删除临时收支记录
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable String id) {
        String userId = LoginUserContext.getUserId();
        temporaryTransactionService.delete(id, userId);
        return Result.success("删除成功");
    }

    /**
     * 更新临时收支记录
     */
    @PutMapping("/{id}")
    public Result<String> update(@PathVariable String id, @RequestBody TemporaryTransactionUpdateRequestDto dto) {
        String userId = LoginUserContext.getUserId();
        temporaryTransactionService.update(id, userId, dto);
        return Result.success("更新成功");
    }

    /**
     * 根据ID查询临时收支记录
     */
    @GetMapping("/{id}")
    public Result<TemporaryTransactionResponseDto> queryById(@PathVariable String id) {
        TemporaryTransactionResponseDto vo = temporaryTransactionService.queryById(id);
        return Result.success(vo);
    }

    /**
     * 查询用户所有临时收支记录
     */
    @GetMapping("/list")
    public Result<List<TemporaryTransactionResponseDto>> queryByUserId() {
        String userId = LoginUserContext.getUserId();
        List<TemporaryTransactionResponseDto> voList = temporaryTransactionService.queryByUserId(userId);
        return Result.success(voList);
    }
}
