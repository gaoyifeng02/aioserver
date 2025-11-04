package com.gaoyifeng.aioserver.app;

import com.gaoyifeng.aioserver.api.dto.blog.request.BlogAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogEditRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.response.BlogGetListResponseDto;
import com.gaoyifeng.aioserver.api.dto.blog.response.BlogPageResponseDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogGetListRequestDto;
import com.gaoyifeng.aioserver.domain.blog.model.entity.Blog;
import com.gaoyifeng.aioserver.domain.blog.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BlogApplicationService {

    @Resource
    private BlogService blogService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Blog createBlog(BlogAddRequestDto requestDto) {
        log.info("应用服务创建博客：title={}", requestDto != null ? requestDto.getTitle() : "null");
        return blogService.createBlog(
                requestDto.getTitle(),
                requestDto.getCateId(),
                requestDto.getCoverImg(),
                requestDto.getContent(),
                requestDto.getState()
        );
    }

    public Blog updateBlog(BlogEditRequestDto requestDto) {
        log.info("应用服务更新博客：id={}, title={}",
                requestDto != null ? requestDto.getId() : "null",
                requestDto != null ? requestDto.getTitle() : "null");
        return blogService.updateBlog(
                requestDto.getId(),
                requestDto.getTitle(),
                requestDto.getCateId(),
                requestDto.getCoverImg(),
                requestDto.getContent(),
                requestDto.getState()
        );
    }

    public void deleteBlog(String id) {
        log.info("应用服务删除博客：id={}", id);
        blogService.deleteBlog(id);
    }

    public void deleteBlogs(List<String> ids) {
        log.info("应用服务批量删除博客：ids={}", ids);
        blogService.deleteBlogs(ids);
    }

    public BlogGetListResponseDto getBlogById(String id) {
        log.info("应用服务查询单个博客：id={}", id);
        Blog blog = blogService.getBlogById(id);
        return blog != null ? convertToResponseDto(blog) : null;
    }

    public List<BlogGetListResponseDto> getAllBlogs() {
        log.info("应用服务查询所有博客");
        List<Blog> blogs = blogService.getAllBlogs();
        return blogs.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    public BlogPageResponseDto getBlogsByPage(BlogGetListRequestDto requestDto) {
        log.info("应用服务分页查询博客：page={}, pageSize={}",
                requestDto != null ? requestDto.getPage() : "null",
                requestDto != null ? requestDto.getPageSize() : "null");

        List<Blog> blogs = blogService.getBlogsByPage(
                requestDto.getPage(),
                requestDto.getPageSize(),
                requestDto.getCateId(),
                requestDto.getTitle(),
                requestDto.getState()
        );

        Long total = blogService.countBlogs(
                requestDto.getCateId(),
                requestDto.getTitle(),
                requestDto.getState()
        );

        BlogPageResponseDto pageResponse = new BlogPageResponseDto();
        pageResponse.setRecords(blogs.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList()));
        pageResponse.setTotal(total);
        pageResponse.setPages((total + requestDto.getPageSize() - 1) / requestDto.getPageSize());
        pageResponse.setCurrent(requestDto.getPage());
        pageResponse.setSize(requestDto.getPageSize());

        return pageResponse;
    }

    public List<BlogGetListResponseDto> getBlogsByCateId(String cateId) {
        log.info("应用服务根据分类ID查询博客：cateId={}", cateId);
        List<Blog> blogs = blogService.getBlogsByCateId(cateId);
        return blogs.stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    private BlogGetListResponseDto convertToResponseDto(Blog blog) {
        BlogGetListResponseDto dto = new BlogGetListResponseDto();
        dto.setId(blog.getId());
        dto.setTitle(blog.getTitle());
        dto.setCateId(blog.getCateId());
        dto.setCateName(blog.getCateName());
        dto.setCoverImg(blog.getCoverImg());
        dto.setContent(blog.getContent());
        dto.setState(blog.getState());
        dto.setCreateTime(blog.getCreateTime() != null ? blog.getCreateTime().format(DATE_FORMATTER) : null);
        dto.setUpdateTime(blog.getUpdateTime() != null ? blog.getUpdateTime().format(DATE_FORMATTER) : null);
        return dto;
    }

}