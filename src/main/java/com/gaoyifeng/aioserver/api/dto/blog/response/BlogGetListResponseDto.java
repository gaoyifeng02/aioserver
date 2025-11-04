package com.gaoyifeng.aioserver.api.dto.blog.response;

import lombok.Data;

@Data
public class BlogGetListResponseDto {

    private String id;

    private String title;

    private String cateId;

    private String cateName;

    private String coverImg;

    private String content;

    private Integer state;

    private String createTime;

    private String updateTime;

}