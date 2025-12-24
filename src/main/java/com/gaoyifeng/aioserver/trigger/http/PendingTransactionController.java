package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.service.IPendingTransactionService;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.dto.PendingTransactionAddDTO;
import com.gaoyifeng.aioserver.types.dto.PendingTransactionUpdateDTO;
import com.gaoyifeng.aioserver.types.vo.PendingTransactionVO;
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
    public Result<String> add(@RequestBody PendingTransactionAddDTO dto) {
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
    public Result<String> update(@PathVariable String id, @RequestBody PendingTransactionUpdateDTO dto) {
        String userId = LoginUserContext.getUserId();
        pendingTransactionService.update(id, userId, dto);
        return Result.success("更新成功");
    }

    /**
     * 根据ID查询待入账
     */
    @GetMapping("/{id}")
    public Result<PendingTransactionVO> queryById(@PathVariable String id) {
        PendingTransactionVO vo = pendingTransactionService.queryById(id);
        return Result.success(vo);
    }

    /**
     * 查询用户所有待入账
     */
    @GetMapping("/list")
    public Result<List<PendingTransactionVO>> queryByUserId() {
        String userId = LoginUserContext.getUserId();
        List<PendingTransactionVO> voList = pendingTransactionService.queryByUserId(userId);
        return Result.success(voList);
    }
}
