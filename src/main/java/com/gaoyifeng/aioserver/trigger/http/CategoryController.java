package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.ICateService;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateDeleteRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateEditRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.response.CateGetListResponseDto;
import com.gaoyifeng.aioserver.domain.model.entity.Category;
import com.gaoyifeng.aioserver.domain.service.CategoryService;
import com.gaoyifeng.aioserver.types.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类控制器 - DDD架构实现
 * 实现分类的增删改查接口
 * 遵循RESTful API规范
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/blog/categories")
public class CategoryController implements ICateService {

    @Resource
    private CategoryService categoryService;

    /**
     * 创建分类 - RESTful POST
     * @param cateAddRequestDto 添加分类请求DTO
     * @return 添加结果
     */
    @Override
    @PostMapping
    public Result add(@RequestBody CateAddRequestDto cateAddRequestDto) {
        try {
            log.info("接收到添加分类请求：cateName={}", cateAddRequestDto != null ? cateAddRequestDto.getCateName() : "null");

            if (cateAddRequestDto == null || !StringUtils.hasText(cateAddRequestDto.getCateName())) {
                return Result.fail("分类名称不能为空");
            }

            categoryService.createCategory(cateAddRequestDto.getCateName());
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
     * 编辑分类 - RESTful PUT
     * @param id 分类ID
     * @param cateEditRequestDto 编辑分类请求DTO
     * @return 编辑结果
     */
    @Override
    @PutMapping("/{id}")
    public Result edit(@PathVariable String id, @RequestBody CateEditRequestDto cateEditRequestDto) {
        try {
            log.info("接收到编辑分类请求：id={}, cateName={}",
                cateEditRequestDto != null ? cateEditRequestDto.getId() : "null",
                cateEditRequestDto != null ? cateEditRequestDto.getCateName() : "null");

            if (cateEditRequestDto == null || !StringUtils.hasText(id)) {
                return Result.fail("分类ID不能为空");
            }
            if (!StringUtils.hasText(cateEditRequestDto.getCateName())) {
                return Result.fail("分类名称不能为空");
            }

            cateEditRequestDto.setId(id);
            categoryService.updateCategory(cateEditRequestDto.getId(), cateEditRequestDto.getCateName());
            log.info("编辑分类成功：id={}, cateName={}", id, cateEditRequestDto.getCateName());
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
     * 删除分类 - RESTful DELETE (支持单个和批量删除)
     * @param id 分类ID (单个删除)
     * @param cateDeleteRequestDto 批量删除请求DTO (可选，用于批量删除)
     * @return 删除结果
     */
    @Override
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id, @RequestBody(required = false) CateDeleteRequestDto cateDeleteRequestDto) {
        try {
            log.info("接收到删除分类请求：id={}, 批量删除={}", id, cateDeleteRequestDto != null ? cateDeleteRequestDto.getIds() : "单个删除");

            // 如果提供了批量删除请求，则执行批量删除
            if (cateDeleteRequestDto != null && cateDeleteRequestDto.getIds() != null && !cateDeleteRequestDto.getIds().isEmpty()) {
                categoryService.deleteCategories(cateDeleteRequestDto.getIds());
                log.info("批量删除分类成功：ids={}", cateDeleteRequestDto.getIds());
            } else {
                // 否则执行单个删除
                categoryService.deleteCategory(id);
                log.info("单个删除分类成功：id={}", id);
            }

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
     * 获取分类列表 - RESTful GET
     * @return 分类列表
     */
    @Override
    @GetMapping
    public Result<List<CateGetListResponseDto>> getList() {
        try {
            log.info("接收到获取分类列表请求");

            List<Category> categories = categoryService.getAllCategories();
            List<CateGetListResponseDto> responseDtoList = categories.stream()
                    .map(this::convertToResponseDto)
                    .collect(Collectors.toList());
            log.info("获取分类列表成功，数量：{}", categories != null ? categories.size() : 0);
            return Result.success(responseDtoList);
        } catch (Exception e) {
            log.error("获取分类列表异常", e);
            return Result.fail("获取分类列表失败");
        }
    }

    private CateGetListResponseDto convertToResponseDto(Category category) {
        CateGetListResponseDto dto = new CateGetListResponseDto();
        dto.setId(category.getId());
        dto.setCateName(category.getCateName());
        dto.setBlogNum(category.getBlogNum() != null ? category.getBlogNum().toString() : "0");
        return dto;
    }
}