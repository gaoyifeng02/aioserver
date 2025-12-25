package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.dto.asset.response.AssetAccountResponseDto;
import com.gaoyifeng.aioserver.api.service.IAssetAccountService;
import com.gaoyifeng.aioserver.infrastructure.threadlocal.LoginUserContext;
import com.gaoyifeng.aioserver.types.common.Result;
import com.gaoyifeng.aioserver.types.common.ResultCode;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * 用户资产账户控制器
 */
@RestController
@RequestMapping("/asset/account")
public class AssetAccountController {

    @Resource
    private IAssetAccountService assetAccountService;

    /**
     * 查询用户资产总览
     * @return 资产总览信息
     */
    @GetMapping
    public Result<AssetAccountResponseDto> queryAssetAccount() {
        // 从ThreadLocal获取用户ID
        String userId = LoginUserContext.getUserId();
        AssetAccountResponseDto vo = assetAccountService.queryAssetAccount(userId);

        if (vo == null) {
            return Result.fail(ResultCode._404.getCode(), "资产账户不存在");
        }

        return Result.success(vo);
    }
}
