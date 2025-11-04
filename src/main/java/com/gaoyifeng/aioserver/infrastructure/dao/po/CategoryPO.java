package com.gaoyifeng.aioserver.infrastructure.dao.po;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryPO {

    private String id;

    private String cateName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer blogNum;
}