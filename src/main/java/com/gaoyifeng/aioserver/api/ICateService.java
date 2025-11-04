package com.gaoyifeng.aioserver.api;

import com.gaoyifeng.aioserver.api.dto.cate.request.CateAddRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateDeleteRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.request.CateEditRequestDto;
import com.gaoyifeng.aioserver.api.dto.cate.response.CateGetListResponseDto;
import com.gaoyifeng.aioserver.types.common.Result;

import java.util.List;

public interface ICateService {

    Result add(CateAddRequestDto cateAddRequestDto);

    Result edit(String id, CateEditRequestDto cateEditRequestDto);

    Result delete(String id, CateDeleteRequestDto cateDeleteRequestDto);

    Result<List<CateGetListResponseDto>> getList();

}
