package com.ratepay.core.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageAbleDto<T> {
    private  List<T> contentList = new ArrayList<>();
    private PageDto page = new PageDto();
}
