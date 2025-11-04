package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.blog.request.BlogAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogDeleteRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogEditRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.request.BlogGetListRequestDto;
import com.gaoyifeng.aioserver.api.dto.blog.response.BlogPageResponseDto;
import com.gaoyifeng.aioserver.types.common.Result;

public interface IBlogService {

    Result add(BlogAddRequestDto blogAddRequestDto);

    Result edit(String id, BlogEditRequestDto blogEditRequestDto);

    Result delete(String id, BlogDeleteRequestDto blogDeleteRequestDto);

    Result<BlogPageResponseDto> getList(BlogGetListRequestDto blogGetListRequestDto);

}
