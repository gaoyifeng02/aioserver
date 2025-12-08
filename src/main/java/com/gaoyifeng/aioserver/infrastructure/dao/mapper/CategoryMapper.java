package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.CategoryPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    int updateById(CategoryPO categoryPO);

    CategoryPO selectById(String id);

    int insert(CategoryPO categoryPO);

    List<CategoryPO> findAll();

    CategoryPO findByName(String cateName);

    int deleteById(String id);

    int deleteByIds(List<String> ids);

    int countByName(String cateName);

    int countByNameAndExcludeId(String cateName, String excludeId);
}