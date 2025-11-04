package com.gaoyifeng.aioserver.api.dto.blog.request;

import lombok.Data;

@Data
public class BlogAddRequestDto {

    private String title;

    private String cateId;

    private String coverImg;

    private String content;

    private Integer state;

}