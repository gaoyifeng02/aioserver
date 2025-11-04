package com.gaoyifeng.aioserver.api.dto.blog.request;

import lombok.Data;

@Data
public class BlogGetListRequestDto {

    private Integer page = 1;

    private Integer pageSize = 10;

    private String cateId;

    private String title;

    private Integer state;

}