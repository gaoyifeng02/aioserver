package com.gaoyifeng.aioserver.domain.blog.adapter.repository;

import com.gaoyifeng.aioserver.domain.blog.model.entity.Category;

import java.util.List;

public interface ICategoryRepository {

    void save(Category category);

    Category findById(String id);

    Category findByName(String cateName);

    List<Category> findAll();

    void deleteById(String id);

    void deleteByIds(List<String> ids);

    boolean existsByName(String cateName);

    boolean existsByNameAndExcludeId(String cateName, String excludeId);
}