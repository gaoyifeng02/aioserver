package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.domain.adapter.repository.ICategoryRepository;
import com.gaoyifeng.aioserver.domain.model.entity.Category;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Resource
    private ICategoryRepository categoryRepository;

    public Category createCategory(String cateName) {
        if (categoryRepository.existsByName(cateName)) {
            throw new IllegalArgumentException("分类名称已存在");
        }

        Category category = new Category(cateName);
        category.setId(UUID.randomUUID().toString().replace("-", ""));
        categoryRepository.save(category);
        return category;
    }

    public Category updateCategory(String id, String cateName) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }

        if (categoryRepository.existsByNameAndExcludeId(cateName, id)) {
            throw new IllegalArgumentException("分类名称已存在");
        }

        category.updateName(cateName);
        categoryRepository.save(category);
        return category;
    }

    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }
        categoryRepository.deleteById(id);
    }

    public void deleteCategories(List<String> ids) {
        for (String id : ids) {
            deleteCategory(id);
        }
    }

    public Category getCategory(String id) {
        Category category = categoryRepository.findById(id);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }
        return category;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}