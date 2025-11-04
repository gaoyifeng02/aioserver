package com.gaoyifeng.aioserver.infrastructure.dao.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogPO {

    private String id;

    private String title;

    private String cateId;

    private String cateName;

    private String coverImg;

    private String content;

    private Integer state;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}