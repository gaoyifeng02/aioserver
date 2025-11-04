package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.CategoryPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Update("UPDATE category SET cate_name = #{po.cateName}, create_time = #{po.createTime}, " +
            "update_time = #{po.updateTime}, blog_num = #{po.blogNum} WHERE id = #{po.id}")
    int updateById(@Param("po") CategoryPO categoryPO);

    @Select("SELECT id, cate_name as cateName, create_time as createTime, update_time as updateTime, blog_num as blogNum FROM category WHERE id = #{id}")
    CategoryPO selectById(@Param("id") String id);

    @Insert("INSERT INTO category (id, cate_name, create_time, update_time, blog_num) " +
            "VALUES (#{po.id}, #{po.cateName}, #{po.createTime}, #{po.updateTime}, #{po.blogNum})")
    int insert(@Param("po") CategoryPO categoryPO);

    @Select("SELECT id, cate_name as cateName, create_time as createTime, update_time as updateTime, blog_num as blogNum FROM category ORDER BY create_time DESC")
    List<CategoryPO> findAll();

    @Select("SELECT id, cate_name as cateName, create_time as createTime, update_time as updateTime, blog_num as blogNum FROM category WHERE cate_name = #{cateName} LIMIT 1")
    CategoryPO findByName(@Param("cateName") String cateName);

    @Delete("DELETE FROM category WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    @Delete("<script>" +
            "DELETE FROM category WHERE id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteByIds(@Param("ids") List<String> ids);

    @Select("SELECT COUNT(1) FROM category WHERE cate_name = #{cateName}")
    int countByName(@Param("cateName") String cateName);

    @Select("<script>" +
            "SELECT COUNT(1) FROM category WHERE cate_name = #{cateName} AND id != #{excludeId}" +
            "</script>")
    int countByNameAndExcludeId(@Param("cateName") String cateName, @Param("excludeId") String excludeId);
}