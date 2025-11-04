package com.gaoyifeng.aioserver.trigger;

import com.gaoyifeng.aioserver.api.ICateService;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateDeleteRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateEditRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.response.CateGetListResponseDto;
import com.gaoyifeng.aioserver.app.CategoryApplicationService;
import com.gaoyifeng.aioserver.types.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 分类控制器 - DDD架构实现
 * 实现分类的增删改查接口
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/category")
public class CategoryController implements ICateService {

    @Resource
    private CategoryApplicationService categoryApplicationService;

    /**
     * 添加分类
     * @param cateAddRequestDto 添加分类请求DTO
     * @return 添加结果
     */
    @Override
    @PostMapping("/add")
    public Result add(@RequestBody CateAddRequestDto cateAddRequestDto) {
        try {
            log.info("接收到添加分类请求：cateName={}", cateAddRequestDto != null ? cateAddRequestDto.getCateName() : "null");

            if (cateAddRequestDto == null || !StringUtils.hasText(cateAddRequestDto.getCateName())) {
                return Result.fail("分类名称不能为空");
            }

            categoryApplicationService.createCategory(cateAddRequestDto.getCateName());
            log.info("添加分类成功：cateName={}", cateAddRequestDto.getCateName());
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("添加分类失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("添加分类异常", e);
            return Result.fail("添加分类失败");
        }
    }

    /**
     * 编辑分类
     * @param cateEditRequestDto 编辑分类请求DTO
     * @return 编辑结果
     */
    @Override
    @PostMapping("/edit")
    public Result edit(@RequestBody CateEditRequestDto cateEditRequestDto) {
        try {
            log.info("接收到编辑分类请求：id={}, cateName={}",
                cateEditRequestDto != null ? cateEditRequestDto.getId() : "null",
                cateEditRequestDto != null ? cateEditRequestDto.getCateName() : "null");

            if (cateEditRequestDto == null || !StringUtils.hasText(cateEditRequestDto.getId())) {
                return Result.fail("分类ID不能为空");
            }
            if (!StringUtils.hasText(cateEditRequestDto.getCateName())) {
                return Result.fail("分类名称不能为空");
            }

            categoryApplicationService.updateCategory(cateEditRequestDto.getId(), cateEditRequestDto.getCateName());
            log.info("编辑分类成功：id={}, cateName={}", cateEditRequestDto.getId(), cateEditRequestDto.getCateName());
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("编辑分类失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("编辑分类异常", e);
            return Result.fail("编辑分类失败");
        }
    }

    /**
     * 删除分类
     * @param cateDeleteRequestDto 删除分类请求DTO
     * @return 删除结果
     */
    @Override
    @PostMapping("/delete")
    public Result delete(@RequestBody CateDeleteRequestDto cateDeleteRequestDto) {
        try {
            log.info("接收到删除分类请求：ids={}", cateDeleteRequestDto != null ? cateDeleteRequestDto.getIds() : "null");

            if (cateDeleteRequestDto == null || cateDeleteRequestDto.getIds() == null || cateDeleteRequestDto.getIds().isEmpty()) {
                return Result.fail("请选择要删除的分类");
            }

            categoryApplicationService.deleteCategories(cateDeleteRequestDto.getIds());
            log.info("删除分类成功：ids={}", cateDeleteRequestDto.getIds());
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("删除分类失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("删除分类异常", e);
            return Result.fail("删除分类失败");
        }
    }

    /**
     * 获取分类列表
     * @return 分类列表
     */
    @Override
    @GetMapping("/list")
    public Result<List<CateGetListResponseDto>> getList() {
        try {
            log.info("接收到获取分类列表请求");

            List<CateGetListResponseDto> categories = categoryApplicationService.getAllCategories();
            log.info("获取分类列表成功，数量：{}", categories != null ? categories.size() : 0);
            return Result.success(categories);
        } catch (Exception e) {
            log.error("获取分类列表异常", e);
            return Result.fail("获取分类列表失败");
        }
    }
}