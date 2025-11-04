package com.gaoyifeng.aioserver.infrastructure.repository;

import com.gaoyifeng.aioserver.domain.blog.adapter.repository.ICategoryRepository;
import com.gaoyifeng.aioserver.domain.blog.model.entity.Category;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.CategoryMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.CategoryPO;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CategoryRepositoryImpl implements ICategoryRepository {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public void save(Category category) {
        CategoryPO categoryPO = convertToPO(category);
        if (category.getId() != null && categoryMapper.selectById(category.getId()) != null) {
            categoryMapper.updateById(categoryPO);
        } else {
            if (categoryPO.getId() == null) {
                categoryPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
            }
            categoryMapper.insert(categoryPO);
        }
    }

    @Override
    public Category findById(String id) {
        if (id == null) return null;
        CategoryPO categoryPO = categoryMapper.selectById(id);
        return convertToEntity(categoryPO);
    }

    @Override
    public Category findByName(String cateName) {
        CategoryPO categoryPO = categoryMapper.findByName(cateName);
        return convertToEntity(categoryPO);
    }

    @Override
    public List<Category> findAll() {
        List<CategoryPO> categoryPOList = categoryMapper.findAll();
        return categoryPOList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(String id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            categoryMapper.deleteByIds(ids);
        }
    }

    @Override
    public boolean existsByName(String cateName) {
        return categoryMapper.countByName(cateName) > 0;
    }

    @Override
    public boolean existsByNameAndExcludeId(String cateName, String excludeId) {
        return categoryMapper.countByNameAndExcludeId(cateName, excludeId) > 0;
    }

    private CategoryPO convertToPO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryPO categoryPO = new CategoryPO();
        categoryPO.setId(category.getId());
        categoryPO.setCateName(category.getCateName());
        categoryPO.setCreateTime(category.getCreateTime());
        categoryPO.setUpdateTime(category.getUpdateTime());
        categoryPO.setBlogNum(category.getBlogNum());
        return categoryPO;
    }

    private Category convertToEntity(CategoryPO categoryPO) {
        if (categoryPO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryPO.getId());
        category.setCateName(categoryPO.getCateName());
        category.setCreateTime(categoryPO.getCreateTime());
        category.setUpdateTime(categoryPO.getUpdateTime());
        category.setBlogNum(categoryPO.getBlogNum());
        return category;
    }
}