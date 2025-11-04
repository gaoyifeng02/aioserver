package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.BlogPO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BlogMapper {

    @Update("UPDATE blog SET title = #{po.title}, cate_id = #{po.cateId}, cate_name = #{po.cateName}, " +
            "cover_img = #{po.coverImg}, content = #{po.content}, state = #{po.state}, " +
            "update_time = #{po.updateTime} WHERE id = #{po.id}")
    int updateById(@Param("po") BlogPO blogPO);

    @Select("SELECT id, title, cate_id as cateId, cate_name as cateName, cover_img as coverImg, " +
            "content, state, create_time as createTime, update_time as updateTime FROM blog WHERE id = #{id}")
    BlogPO selectById(@Param("id") String id);

    @Insert("INSERT INTO blog (id, title, cate_id, cate_name, cover_img, content, state, create_time, update_time) " +
            "VALUES (#{po.id}, #{po.title}, #{po.cateId}, #{po.cateName}, #{po.coverImg}, #{po.content}, " +
            "#{po.state}, #{po.createTime}, #{po.updateTime})")
    int insert(@Param("po") BlogPO blogPO);

    @Select("SELECT id, title, cate_id as cateId, cate_name as cateName, cover_img as coverImg, " +
            "content, state, create_time as createTime, update_time as updateTime " +
            "FROM blog ORDER BY create_time DESC")
    List<BlogPO> findAll();

    @Select("<script>" +
            "SELECT id, title, cate_id as cateId, cate_name as cateName, cover_img as coverImg, " +
            "content, state, create_time as createTime, update_time as updateTime FROM blog " +
            "<where>" +
            "<if test='cateId != null and cateId != \"\"'>" +
            "AND cate_id = #{cateId}" +
            "</if>" +
            "<if test='title != null and title != \"\"'>" +
            "AND title LIKE CONCAT('%', #{title}, '%')" +
            "</if>" +
            "<if test='state != null'>" +
            "AND state = #{state}" +
            "</if>" +
            "</where>" +
            "ORDER BY create_time DESC " +
            "LIMIT #{pageSize} OFFSET #{pageSize} * (#{page} - 1)" +
            "</script>")
    List<BlogPO> selectByPage(@Param("page") Integer page,
                             @Param("pageSize") Integer pageSize,
                             @Param("cateId") String cateId,
                             @Param("title") String title,
                             @Param("state") Integer state);

    @Select("<script>" +
            "SELECT COUNT(*) FROM blog " +
            "<where>" +
            "<if test='cateId != null and cateId != \"\"'>" +
            "AND cate_id = #{cateId}" +
            "</if>" +
            "<if test='title != null and title != \"\"'>" +
            "AND title LIKE CONCAT('%', #{title}, '%')" +
            "</if>" +
            "<if test='state != null'>" +
            "AND state = #{state}" +
            "</if>" +
            "</where>" +
            "</script>")
    Long countByCondition(@Param("cateId") String cateId,
                         @Param("title") String title,
                         @Param("state") Integer state);

    @Select("SELECT id, title, cate_id as cateId, cate_name as cateName, cover_img as coverImg, " +
            "content, state, create_time as createTime, update_time as updateTime " +
            "FROM blog WHERE cate_id = #{cateId} ORDER BY create_time DESC")
    List<BlogPO> selectByCateId(@Param("cateId") String cateId);

    @Delete("DELETE FROM blog WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    @Delete("<script>" +
            "DELETE FROM blog WHERE id IN " +
            "<foreach item='id' collection='ids' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteByIds(@Param("ids") List<String> ids);

}