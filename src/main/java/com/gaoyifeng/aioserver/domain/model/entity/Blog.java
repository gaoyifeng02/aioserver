package com.gaoyifeng.aioserver.domain.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Blog {

    private String id;

    private String title;

    private String cateId;

    private String cateName;

    private String coverImg;

    private String content;

    private Integer state;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public Blog() {}

    public Blog(String title, String cateId, String coverImg, String content, Integer state) {
        this.title = title;
        this.cateId = cateId;
        this.coverImg = coverImg;
        this.content = content;
        this.state = state != null ? state : 1;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public void updateInfo(String title, String cateId, String coverImg, String content, Integer state) {
        this.title = title;
        this.cateId = cateId;
        this.coverImg = coverImg;
        this.content = content;
        this.state = state != null ? state : 1;
        this.updateTime = LocalDateTime.now();
    }

    public void updateCategoryName(String cateName) {
        this.cateName = cateName;
        this.updateTime = LocalDateTime.now();
    }

    public boolean isPublished() {
        return this.state != null && this.state == 1;
    }

    public void publish() {
        this.state = 1;
        this.updateTime = LocalDateTime.now();
    }

    public void draft() {
        this.state = 0;
        this.updateTime = LocalDateTime.now();
    }

}