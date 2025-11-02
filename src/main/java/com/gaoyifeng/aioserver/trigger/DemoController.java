package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Demo控制器，演示五种HTTP方法的使用
 * 包含path、query、body、header等不同类型的参数传递
 * 
 * @author 高艺峰
 */
@RestController
@RequestMapping("/api/demo")
public class DemoController {

    /**
     * GET方法 - 支持path参数和query参数
     * 示例: GET /api/demo/users/123?name=张三&age=25
     */
    @GetMapping("/users/{id}")
    public Result<Map<String, Object>> getUser(
            @PathVariable("id") String id,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "age", required = false) Integer age) {
        
        // 参数验证
        if (id == null || id.trim().isEmpty()) {
            return Result.fail(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("name", name != null ? name : "默认用户");
        data.put("age", age != null ? age : 18);
        data.put("method", "GET");
        
        return Result.success(data);
    }

    /**
     * POST方法 - 支持body参数和header参数
     * 示例: POST /api/demo/users
     * Headers: Authorization: Bearer token123, Content-Type: application/json
     * Body: {"name": "张三", "age": 25, "email": "zhangsan@example.com"}
     */
    @PostMapping("/users")
    public Result<Map<String, Object>> createUser(
            @RequestBody Map<String, Object> userInfo,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "Content-Type", required = false) String contentType) {
        
        // 参数验证
        if (userInfo == null || userInfo.isEmpty()) {
            return Result.fail(ResultCode.PARAM_ERROR, "用户信息不能为空");
        }
        
        if (!userInfo.containsKey("name") || userInfo.get("name") == null) {
            return Result.fail(ResultCode.PARAM_ERROR, "用户名不能为空");
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("userInfo", userInfo);
        data.put("authorization", authorization);
        data.put("contentType", contentType);
        data.put("method", "POST");
        data.put("message", "用户创建成功");
        
        return Result.success(data);
    }

    /**
     * PUT方法 - 支持path参数、body参数和header参数
     * 示例: PUT /api/demo/users/123
     * Headers: Authorization: Bearer token123, X-Request-ID: req-456
     * Body: {"name": "李四", "age": 30, "email": "lisi@example.com"}
     */
    @PutMapping("/users/{id}")
    public Result<Map<String, Object>> updateUser(
            @PathVariable("id") String id,
            @RequestBody Map<String, Object> userInfo,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "X-Request-ID", required = false) String requestId) {
        
        // 参数验证
        if (id == null || id.trim().isEmpty()) {
            return Result.fail(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        if (userInfo == null || userInfo.isEmpty()) {
            return Result.fail(ResultCode.PARAM_ERROR, "更新信息不能为空");
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("userInfo", userInfo);
        data.put("authorization", authorization);
        data.put("requestId", requestId);
        data.put("method", "PUT");
        data.put("message", "用户更新成功");
        
        return Result.success(data);
    }

    /**
     * DELETE方法 - 支持path参数和header参数
     * 示例: DELETE /api/demo/users/123
     * Headers: Authorization: Bearer token123, X-Delete-Reason: 用户注销
     */
    @DeleteMapping("/users/{id}")
    public Result<Map<String, Object>> deleteUser(
            @PathVariable("id") String id,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "X-Delete-Reason", required = false) String deleteReason) {
        
        // 参数验证
        if (id == null || id.trim().isEmpty()) {
            return Result.fail(ResultCode.PARAM_ERROR, "用户ID不能为空");
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("authorization", authorization);
        data.put("deleteReason", deleteReason);
        data.put("method", "DELETE");
        data.put("message", "用户删除成功");
        
        return Result.success(data);
    }

    /**
     * PATCH方法 - 支持query参数、body参数和header参数
     * 示例: PATCH /api/demo/users?action=activate&notify=true
     * Headers: Authorization: Bearer token123, X-Patch-Version: v1.0
     * Body: {"status": "active", "lastLoginTime": "2025-01-24T10:30:00"}
     */
    @PatchMapping("/users")
    public Result<Map<String, Object>> patchUser(
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "notify", required = false) Boolean notify,
            @RequestBody(required = false) Map<String, Object> patchData,
            @RequestHeader(value = "Authorization", required = false) String authorization,
            @RequestHeader(value = "X-Patch-Version", required = false) String patchVersion) {
        
        // 参数验证
        if (action == null || action.trim().isEmpty()) {
            return Result.fail(ResultCode.PARAM_ERROR, "操作类型(action)不能为空");
        }
        
        Map<String, Object> data = new HashMap<>();
        data.put("action", action);
        data.put("notify", notify != null ? notify : false);
        data.put("patchData", patchData);
        data.put("authorization", authorization);
        data.put("patchVersion", patchVersion);
        data.put("method", "PATCH");
        data.put("message", "用户部分更新成功");
        
        return Result.success(data);
    }
}
