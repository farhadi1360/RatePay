package com.ratepay.core.service;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.core.dto.PageAbleDto;
import com.ratepay.core.dto.PageDto;
import com.ratepay.core.exception.BaseException;
import com.ratepay.core.exception.GeneralException;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface MainServiceSQLMode<M, ID extends Serializable>{
    PageDto findAllTable(M filter, Pageable pageable);
    PageDto findAllSelect(M filter, Pageable pageable);
    Optional<List<M>>findAll(M filter) throws BaseException;
    Optional<List<M>> getAll() throws GeneralException;
    Optional<PageAbleDto<M>> getAllByPaging(PageDto pageDto) throws GeneralException;
    Long countAll(M filter);
    Optional<M> findById(ID id);
    Optional<M> save(M entity);
    void deleteById(ID id);
}