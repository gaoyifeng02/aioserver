package com.gaoyifeng.aioserver.api.dto.blog.response;

import lombok.Data;

import java.util.List;

@Data
public class BlogPageResponseDto {

    private List<BlogGetListResponseDto> records;

    private Long total;

    private Long pages;

    private Integer current;

    private Integer size;

}