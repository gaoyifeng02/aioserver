package com.gaoyifeng.aioserver.types.common;

import java.util.List;

/**
 * 分页结果数据传输对象
 * 
 * @author 高艺峰
 * @since 2025-01-24
 * @description 通用分页结果封装类
 * @param <T> 数据类型
 */
public class PageResult<T> {

    /**
     * 数据列表
     */
    private List<T> data;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int totalPages;

    /**
     * 是否有下一页
     */
    private boolean hasNext;

    /**
     * 是否有上一页
     */
    private boolean hasPrevious;

    /**
     * 是否为第一页
     */
    private boolean isFirst;

    /**
     * 是否为最后一页
     */
    private boolean isLast;

    // 构造函数
    public PageResult() {}

    public PageResult(List<T> data, long total, int pageNum, int pageSize) {
        this.data = data;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        
        // 计算总页数
        this.totalPages = (int) Math.ceil((double) total / pageSize);
        
        // 计算分页状态
        this.hasNext = pageNum < totalPages;
        this.hasPrevious = pageNum > 1;
        this.isFirst = pageNum == 1;
        this.isLast = pageNum == totalPages || totalPages == 0;
    }

    // Getter和Setter方法
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    /**
     * 创建空的分页结果
     * 
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param <T> 数据类型
     * @return 空的分页结果
     */
    public static <T> PageResult<T> empty(int pageNum, int pageSize) {
        return new PageResult<>(null, 0, pageNum, pageSize);
    }

    /**
     * 创建分页结果（不分页，返回所有数据）
     * 
     * @param data 数据列表
     * @param <T> 数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(List<T> data) {
        return new PageResult<>(data, data != null ? data.size() : 0, 1, data != null ? data.size() : 0);
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "data=" + (data != null ? data.size() + " items" : "null") +
                ", total=" + total +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", totalPages=" + totalPages +
                ", hasNext=" + hasNext +
                ", hasPrevious=" + hasPrevious +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                '}';
    }
}