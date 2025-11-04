package com.gaoyifeng.aioserver.api.dto.blog.request;

import lombok.Data;

import java.util.List;

@Data
public class BlogDeleteRequestDto {

    private List<String> ids;

}