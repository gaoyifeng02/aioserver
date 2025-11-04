package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.CategoryPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {

    int updateById(@Param("po") CategoryPO categoryPO);

    CategoryPO selectById(@Param("id") String id);

    int insert(@Param("po") CategoryPO categoryPO);
}