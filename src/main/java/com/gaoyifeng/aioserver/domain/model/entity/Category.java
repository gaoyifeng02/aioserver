package com.gaoyifeng.aioserver.domain.model.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    private String id;

    private String cateName;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer blogNum;

    public Category() {}

    public Category(String cateName) {
        this.cateName = cateName;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        this.blogNum = 0;
    }

    public void updateName(String newName) {
        this.cateName = newName;
        this.updateTime = LocalDateTime.now();
    }

    public void increaseBlogCount() {
        this.blogNum = (this.blogNum == null ? 0 : this.blogNum) + 1;
    }

    public void decreaseBlogCount() {
        this.blogNum = (this.blogNum == null || this.blogNum <= 0) ? 0 : this.blogNum - 1;
    }
}