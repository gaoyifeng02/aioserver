package com.gaoyifeng.aioserver.infrastructure.repository;

import com.gaoyifeng.aioserver.domain.blog.adapter.repository.IBlogRepository;
import com.gaoyifeng.aioserver.domain.blog.model.entity.Blog;
import com.gaoyifeng.aioserver.infrastructure.dao.mapper.BlogMapper;
import com.gaoyifeng.aioserver.infrastructure.dao.po.BlogPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class BlogRepositoryImpl implements IBlogRepository {

    @Resource
    private BlogMapper blogMapper;

    @Override
    public void save(Blog blog) {
        BlogPO blogPO = convertToPO(blog);
        if (blog.getId() != null && blogMapper.selectById(blog.getId()) != null) {
            blogMapper.updateById(blogPO);
        } else {
            if (blogPO.getId() == null) {
                blogPO.setId(java.util.UUID.randomUUID().toString().replace("-", ""));
            }
            blogMapper.insert(blogPO);
        }
    }

    @Override
    public Blog findById(String id) {
        if (id == null) return null;
        BlogPO blogPO = blogMapper.selectById(id);
        return convertToEntity(blogPO);
    }

    @Override
    public void deleteById(String id) {
        blogMapper.deleteById(id);
    }

    @Override
    public void deleteByIds(List<String> ids) {
        if (ids != null && !ids.isEmpty()) {
            blogMapper.deleteByIds(ids);
        }
    }

    @Override
    public List<Blog> findAll() {
        List<BlogPO> blogPOList = blogMapper.findAll();
        return blogPOList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<Blog> findByPage(Integer page, Integer pageSize, String cateId, String title, Integer state) {
        Integer offset = (page - 1) * pageSize;
        List<BlogPO> blogPOList = blogMapper.selectByPage(offset, pageSize, cateId, title, state);
        return blogPOList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Long count(String cateId, String title, Integer state) {
        return blogMapper.countByCondition(cateId, title, state);
    }

    @Override
    public List<Blog> findByCateId(String cateId) {
        List<BlogPO> blogPOList = blogMapper.selectByCateId(cateId);
        return blogPOList.stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());
    }

    private BlogPO convertToPO(Blog blog) {
        if (blog == null) {
            return null;
        }
        BlogPO blogPO = new BlogPO();
        blogPO.setId(blog.getId());
        blogPO.setTitle(blog.getTitle());
        blogPO.setCateId(blog.getCateId());
        blogPO.setCateName(blog.getCateName());
        blogPO.setCoverImg(blog.getCoverImg());
        blogPO.setContent(blog.getContent());
        blogPO.setState(blog.getState());
        blogPO.setCreateTime(blog.getCreateTime());
        blogPO.setUpdateTime(blog.getUpdateTime());
        return blogPO;
    }

    private Blog convertToEntity(BlogPO blogPO) {
        if (blogPO == null) {
            return null;
        }
        Blog blog = new Blog();
        blog.setId(blogPO.getId());
        blog.setTitle(blogPO.getTitle());
        blog.setCateId(blogPO.getCateId());
        blog.setCateName(blogPO.getCateName());
        blog.setCoverImg(blogPO.getCoverImg());
        blog.setContent(blogPO.getContent());
        blog.setState(blogPO.getState());
        blog.setCreateTime(blogPO.getCreateTime());
        blog.setUpdateTime(blogPO.getUpdateTime());
        return blog;
    }
}