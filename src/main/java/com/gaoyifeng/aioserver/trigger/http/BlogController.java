package com.gaoyifeng.aioserver.trigger.http;

import com.gaoyifeng.aioserver.api.IBlogService;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogDeleteRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogEditRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogGetListRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.response.BlogPageResponseDto;
import com.gaoyifeng.aioserver.api.dto.blog.response.BlogGetListResponseDto;
import com.gaoyifeng.aioserver.domain.model.entity.Blog;
import com.gaoyifeng.aioserver.domain.service.BlogService;
import com.gaoyifeng.aioserver.types.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客控制器 - DDD架构实现
 * 实现博客的增删改查接口，支持分页查询
 * 遵循RESTful API规范
 */
@Slf4j
@RestController
@RequestMapping("/blog/blogs")
public class BlogController implements IBlogService {

    @Resource
    private BlogService blogService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 创建博客 - RESTful POST
     * @param blogAddRequestDto 添加博客请求DTO
     * @return 添加结果
     */
    @Override
    @PostMapping
    public Result add(@RequestBody BlogAddRequestDto blogAddRequestDto) {
        try {
            log.info("接收到添加博客请求：title={}, cateId={}",
                    blogAddRequestDto != null ? blogAddRequestDto.getTitle() : "null",
                    blogAddRequestDto != null ? blogAddRequestDto.getCateId() : "null");

            if (blogAddRequestDto == null) {
                return Result.fail("请求参数不能为空");
            }

            if (!StringUtils.hasText(blogAddRequestDto.getTitle())) {
                return Result.fail("博客标题不能为空");
            }

            if (!StringUtils.hasText(blogAddRequestDto.getCateId())) {
                return Result.fail("分类ID不能为空");
            }

            blogService.createBlog(
                    blogAddRequestDto.getTitle(),
                    blogAddRequestDto.getCateId(),
                    blogAddRequestDto.getCoverImg(),
                    blogAddRequestDto.getContent(),
                    blogAddRequestDto.getState()
            );
            log.info("添加博客成功：title={}", blogAddRequestDto.getTitle());
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("添加博客失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("添加博客异常", e);
            return Result.fail("添加博客失败");
        }
    }

    /**
     * 编辑博客 - RESTful PUT
     * @param id 博客ID
     * @param blogEditRequestDto 编辑博客请求DTO
     * @return 编辑结果
     */
    @Override
    @PutMapping("/{id}")
    public Result edit(@PathVariable String id, @RequestBody BlogEditRequestDto blogEditRequestDto) {
        try {
            log.info("接收到编辑博客请求：id={}, title={}, cateId={}",
                    blogEditRequestDto != null ? blogEditRequestDto.getId() : "null",
                    blogEditRequestDto != null ? blogEditRequestDto.getTitle() : "null",
                    blogEditRequestDto != null ? blogEditRequestDto.getCateId() : "null");

            if (blogEditRequestDto == null) {
                return Result.fail("请求参数不能为空");
            }

            if (!StringUtils.hasText(id)) {
                return Result.fail("博客ID不能为空");
            }

            if (!StringUtils.hasText(blogEditRequestDto.getTitle())) {
                return Result.fail("博客标题不能为空");
            }

            if (!StringUtils.hasText(blogEditRequestDto.getCateId())) {
                return Result.fail("分类ID不能为空");
            }

            blogEditRequestDto.setId(id);
            blogService.updateBlog(
                    id,
                    blogEditRequestDto.getTitle(),
                    blogEditRequestDto.getCateId(),
                    blogEditRequestDto.getCoverImg(),
                    blogEditRequestDto.getContent(),
                    blogEditRequestDto.getState()
            );
            log.info("编辑博客成功：id={}, title={}", id, blogEditRequestDto.getTitle());
            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("编辑博客失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("编辑博客异常", e);
            return Result.fail("编辑博客失败");
        }
    }

    /**
     * 删除博客 - RESTful DELETE (支持单个和批量删除)
     * @param id 博客ID (单个删除)
     * @param blogDeleteRequestDto 批量删除请求DTO (可选，用于批量删除)
     * @return 删除结果
     */
    @Override
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id, @RequestBody(required = false) BlogDeleteRequestDto blogDeleteRequestDto) {
        try {
            log.info("接收到删除博客请求：id={}, 批量删除={}", id, blogDeleteRequestDto != null ? blogDeleteRequestDto.getIds() : "单个删除");

            // 如果提供了批量删除请求，则执行批量删除
            if (blogDeleteRequestDto != null && blogDeleteRequestDto.getIds() != null && !blogDeleteRequestDto.getIds().isEmpty()) {
                blogService.deleteBlogs(blogDeleteRequestDto.getIds());
                log.info("批量删除博客成功：ids={}", blogDeleteRequestDto.getIds());
            } else {
                // 否则执行单个删除
                blogService.deleteBlog(id);
                log.info("单个删除博客成功：id={}", id);
            }

            return Result.success();
        } catch (IllegalArgumentException e) {
            log.warn("删除博客失败: {}", e.getMessage());
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("删除博客异常", e);
            return Result.fail("删除博客失败");
        }
    }

    /**
     * 获取博客列表 - RESTful GET（支持分页和条件查询）
     * @param blogGetListRequestDto 获取博客列表请求DTO
     * @return 博客分页列表
     */
    @Override
    @GetMapping
    public Result<BlogPageResponseDto> getList(BlogGetListRequestDto blogGetListRequestDto) {
        try {
            log.info("接收到获取博客列表请求：page={}, pageSize={}, cateId={}, title={}, state={}",
                    blogGetListRequestDto != null ? blogGetListRequestDto.getPage() : "null",
                    blogGetListRequestDto != null ? blogGetListRequestDto.getPageSize() : "null",
                    blogGetListRequestDto != null ? blogGetListRequestDto.getCateId() : "null",
                    blogGetListRequestDto != null ? blogGetListRequestDto.getTitle() : "null",
                    blogGetListRequestDto != null ? blogGetListRequestDto.getState() : "null");

            if (blogGetListRequestDto == null) {
                blogGetListRequestDto = new BlogGetListRequestDto();
            }

            List<Blog> blogs = blogService.getBlogsByPage(
                    blogGetListRequestDto.getPage(),
                    blogGetListRequestDto.getPageSize(),
                    blogGetListRequestDto.getCateId(),
                    blogGetListRequestDto.getTitle(),
                    blogGetListRequestDto.getState()
            );

            Long total = blogService.countBlogs(
                    blogGetListRequestDto.getCateId(),
                    blogGetListRequestDto.getTitle(),
                    blogGetListRequestDto.getState()
            );

            BlogPageResponseDto pageResponse = new BlogPageResponseDto();
            pageResponse.setRecords(blogs.stream()
                    .map(this::convertToResponseDto)
                    .collect(Collectors.toList()));
            pageResponse.setTotal(total);
            pageResponse.setPages((total + blogGetListRequestDto.getPageSize() - 1) / blogGetListRequestDto.getPageSize());
            pageResponse.setCurrent(blogGetListRequestDto.getPage());
            pageResponse.setSize(blogGetListRequestDto.getPageSize());
            log.info("获取博客列表成功，总数：{}", pageResponse != null ? pageResponse.getTotal() : 0);
            return Result.success(pageResponse);
        } catch (Exception e) {
            log.error("获取博客列表异常", e);
            return Result.fail("获取博客列表失败");
        }
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