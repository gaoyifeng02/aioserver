package com.gaoyifeng.aioserver.domain.service;

import com.gaoyifeng.aioserver.domain.adapter.repository.IBlogRepository;
import com.gaoyifeng.aioserver.domain.adapter.repository.ICategoryRepository;
import com.gaoyifeng.aioserver.domain.model.entity.Blog;
import com.gaoyifeng.aioserver.domain.model.entity.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BlogService {

    @Resource
    private IBlogRepository blogRepository;

    @Resource
    private ICategoryRepository categoryRepository;

    public Blog createBlog(String title, String cateId, String coverImg, String content, Integer state) {
        log.info("创建博客：title={}, cateId={}", title, cateId);

        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("博客标题不能为空");
        }

        if (!StringUtils.hasText(cateId)) {
            throw new IllegalArgumentException("分类ID不能为空");
        }

        Category category = categoryRepository.findById(cateId);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }

        Blog blog = new Blog(title, cateId, coverImg, content, state);
        blog.setCateName(category.getCateName());
        blog.setId(UUID.randomUUID().toString().replace("-", ""));

        blogRepository.save(blog);

        category.increaseBlogCount();
        categoryRepository.save(category);

        log.info("创建博客成功：id={}", blog.getId());
        return blog;
    }

    public Blog updateBlog(String id, String title, String cateId, String coverImg, String content, Integer state) {
        log.info("更新博客：id={}, title={}, cateId={}", id, title, cateId);

        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("博客ID不能为空");
        }

        Blog blog = blogRepository.findById(id);
        if (blog == null) {
            throw new IllegalArgumentException("博客不存在");
        }

        String oldCateId = blog.getCateId();

        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("博客标题不能为空");
        }

        if (!StringUtils.hasText(cateId)) {
            throw new IllegalArgumentException("分类ID不能为空");
        }

        Category category = categoryRepository.findById(cateId);
        if (category == null) {
            throw new IllegalArgumentException("分类不存在");
        }

        blog.updateInfo(title, cateId, coverImg, content, state);
        blog.setCateName(category.getCateName());

        blogRepository.save(blog);

        if (!oldCateId.equals(cateId)) {
            Category oldCategory = categoryRepository.findById(oldCateId);
            if (oldCategory != null) {
                oldCategory.decreaseBlogCount();
                categoryRepository.save(oldCategory);
            }

            category.increaseBlogCount();
            categoryRepository.save(category);
        }

        log.info("更新博客成功：id={}", blog.getId());
        return blog;
    }

    public void deleteBlog(String id) {
        log.info("删除博客：id={}", id);

        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("博客ID不能为空");
        }

        Blog blog = blogRepository.findById(id);
        if (blog == null) {
            throw new IllegalArgumentException("博客不存在");
        }

        String cateId = blog.getCateId();

        blogRepository.deleteById(id);

        Category category = categoryRepository.findById(cateId);
        if (category != null) {
            category.decreaseBlogCount();
            categoryRepository.save(category);
        }

        log.info("删除博客成功：id={}", id);
    }

    public void deleteBlogs(List<String> ids) {
        log.info("批量删除博客：ids={}", ids);

        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("请选择要删除的博客");
        }

        for (String id : ids) {
            deleteBlog(id);
        }

        log.info("批量删除博客成功：ids={}", ids);
    }

    public Blog getBlogById(String id) {
        log.info("查询博客：id={}", id);

        if (!StringUtils.hasText(id)) {
            throw new IllegalArgumentException("博客ID不能为空");
        }

        Blog blog = blogRepository.findById(id);
        if (blog == null) {
            throw new IllegalArgumentException("博客不存在");
        }
        return blog;
    }

    public List<Blog> getAllBlogs() {
        log.info("查询所有博客");
        return blogRepository.findAll();
    }

    public List<Blog> getBlogsByPage(Integer page, Integer pageSize, String cateId, String title, Integer state) {
        log.info("分页查询博客：page={}, pageSize={}, cateId={}, title={}, state={}",
                page, pageSize, cateId, title, state);

        if (page == null || page < 1) {
            page = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        return blogRepository.findByPage(page, pageSize, cateId, title, state);
    }

    public Long countBlogs(String cateId, String title, Integer state) {
        log.info("统计博客数量：cateId={}, title={}, state={}", cateId, title, state);
        return blogRepository.count(cateId, title, state);
    }

    public List<Blog> getBlogsByCateId(String cateId) {
        log.info("根据分类ID查询博客：cateId={}", cateId);

        if (!StringUtils.hasText(cateId)) {
            throw new IllegalArgumentException("分类ID不能为空");
        }

        return blogRepository.findByCateId(cateId);
    }

}