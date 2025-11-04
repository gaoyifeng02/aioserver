package com.gaoyifeng.aioserver.app;

import com.gaoyifeng.aioserver.api.dto.cate.response.CateGetListResponseDto;
import com.gaoyifeng.aioserver.domain.blog.model.entity.Category;
import com.gaoyifeng.aioserver.domain.blog.service.CategoryService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryApplicationService {

    @Resource
    private CategoryService categoryService;

    public Category createCategory(String cateName) {
        return categoryService.createCategory(cateName);
    }

    public Category updateCategory(String id, String cateName) {
        return categoryService.updateCategory(id, cateName);
    }

    public void deleteCategory(String id) {
        categoryService.deleteCategory(id);
    }

    public void deleteCategories(List<String> ids) {
        categoryService.deleteCategories(ids);
    }

    public List<CateGetListResponseDto> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return categories.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private CateGetListResponseDto convertToResponseDto(Category category) {
        CateGetListResponseDto dto = new CateGetListResponseDto();
        dto.setId(category.getId());
        dto.setCateName(category.getCateName());
        dto.setBlogNum(category.getBlogNum() != null ? category.getBlogNum().toString() : "0");
        return dto;
    }
}