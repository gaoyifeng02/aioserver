package com.gaoyifeng.aioserver.domain.adapter.repository;

import com.gaoyifeng.aioserver.domain.model.entity.Blog;

import java.util.List;

public interface IBlogRepository {

    void save(Blog blog);

    Blog findById(String id);

    List<Blog> findAll();

    void deleteById(String id);

    void deleteByIds(List<String> ids);

    List<Blog> findByPage(Integer page, Integer pageSize, String cateId, String title, Integer state);

    Long count(String cateId, String title, Integer state);

    List<Blog> findByCateId(String cateId);

}