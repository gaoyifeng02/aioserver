package com.gaoyifeng.aioserver.infrastructure.dao.mapper;

import com.gaoyifeng.aioserver.infrastructure.dao.po.BlogPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper {

    int updateById(BlogPO blogPO);

    BlogPO selectById(String id);

    int insert(BlogPO blogPO);

    List<BlogPO> findAll();

    List<BlogPO> selectByPage(Integer offset, Integer pageSize, String cateId, String title, Integer state);

    Long countByCondition(String cateId, String title, Integer state);

    List<BlogPO> selectByCateId(String cateId);

    int deleteById(String id);

    int deleteByIds(List<String> ids);

}