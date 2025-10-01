package com.gaoyifeng.aioserver.types.common;

/**
 * 分页请求数据传输对象
 * 
 * @author 高艺峰
 * @since 2025-01-24
 * @description 通用分页请求参数封装类
 */
public class PageRequest {

    /**
     * 页码，从1开始
     */
    private int pageNum = 1;

    /**
     * 每页大小
     */
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 排序方向：ASC/DESC
     */
    private String sortDir = "ASC";

    /**
     * 搜索关键字
     */
    private String keyword;

    // 构造函数
    public PageRequest() {}

    public PageRequest(int pageNum, int pageSize) {
        this.pageNum = Math.max(1, pageNum);
        this.pageSize = Math.max(1, Math.min(100, pageSize)); // 限制最大页大小为100
    }

    public PageRequest(int pageNum, int pageSize, String sortBy, String sortDir) {
        this(pageNum, pageSize);
        this.sortBy = sortBy;
        this.sortDir = sortDir != null && sortDir.equalsIgnoreCase("DESC") ? "DESC" : "ASC";
    }

    // Getter和Setter方法
    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = Math.max(1, pageNum);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = Math.max(1, Math.min(100, pageSize));
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDir() {
        return sortDir;
    }

    public void setSortDir(String sortDir) {
        this.sortDir = sortDir != null && sortDir.equalsIgnoreCase("DESC") ? "DESC" : "ASC";
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    /**
     * 获取偏移量（用于SQL LIMIT OFFSET）
     * 
     * @return 偏移量
     */
    public int getOffset() {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 获取排序子句（用于SQL ORDER BY）
     * 
     * @return 排序子句，如果没有排序字段则返回null
     */
    public String getOrderBy() {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return null;
        }
        return sortBy + " " + sortDir;
    }

    /**
     * 是否有排序
     * 
     * @return 是否有排序
     */
    public boolean hasSort() {
        return sortBy != null && !sortBy.trim().isEmpty();
    }

    /**
     * 是否有搜索关键字
     * 
     * @return 是否有搜索关键字
     */
    public boolean hasKeyword() {
        return keyword != null && !keyword.trim().isEmpty();
    }

    /**
     * 获取去除空格的关键字
     * 
     * @return 去除空格的关键字
     */
    public String getTrimmedKeyword() {
        return keyword != null ? keyword.trim() : null;
    }

    /**
     * 获取用于LIKE查询的关键字（前后加%）
     * 
     * @return LIKE查询关键字
     */
    public String getLikeKeyword() {
        String trimmed = getTrimmedKeyword();
        return trimmed != null ? "%" + trimmed + "%" : null;
    }

    /**
     * 创建默认分页请求
     * 
     * @return 默认分页请求
     */
    public static PageRequest of() {
        return new PageRequest();
    }

    /**
     * 创建分页请求
     * 
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页请求
     */
    public static PageRequest of(int pageNum, int pageSize) {
        return new PageRequest(pageNum, pageSize);
    }

    /**
     * 创建带排序的分页请求
     * 
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @return 分页请求
     */
    public static PageRequest of(int pageNum, int pageSize, String sortBy, String sortDir) {
        return new PageRequest(pageNum, pageSize, sortBy, sortDir);
    }

    @Override
    public String toString() {
        return "PageRequest{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", sortDir='" + sortDir + '\'' +
                ", keyword='" + keyword + '\'' +
                '}';
    }
}