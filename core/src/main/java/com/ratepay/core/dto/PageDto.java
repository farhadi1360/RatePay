package com.ratepay.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    public PageDto(long recordsTotal, long recordsFiltered, Object data)
    {
        this.totalElement = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }
    int pageNumber;
    int pageSize;
    Long totalElement;
    String sortBy;
    Sort.Direction direction = Sort.Direction.DESC;//decs or ase
    private long recordsFiltered;
    private Object data;
    public String getSortBy() {
        return StringUtils.isEmpty(sortBy) ? "create" : sortBy;
    }
}
