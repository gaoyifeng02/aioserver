package com.gaoyifeng.aioserver.api.dto.cate.request;


import lombok.Data;

import java.util.List;

@Data
public class CateDeleteRequestDto {

    private List<String> ids;

}
