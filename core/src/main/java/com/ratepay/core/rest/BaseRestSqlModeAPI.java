package com.ratepay.core.rest;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.core.dto.BaseResponseDTO;
import com.ratepay.core.dto.PageAbleDto;
import com.ratepay.core.dto.PageDto;
import com.ratepay.core.exception.GeneralException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public interface BaseRestSqlModeAPI<M, ID extends Serializable> {
    @PostMapping(value = {"/add"})
    BaseResponseDTO<M> save(@RequestBody M model) throws GeneralException;

    @PutMapping(value = "/edit")
    BaseResponseDTO<M> update(@RequestBody @Valid M model)throws GeneralException;

    @GetMapping(value = {"/get-by-id"})
    BaseResponseDTO<M> findByUniqueId(@RequestParam ID uniqueId) throws GeneralException;


    @PostMapping(value = {"/list-paging"})
    BaseResponseDTO<PageAbleDto<M>> getAllByPaging(@RequestBody PageDto dto) throws GeneralException;

    @GetMapping(value = {"/list"})
    BaseResponseDTO<List<M>> getAll() throws GeneralException;


    @DeleteMapping(value = {"/remove"})
    BaseResponseDTO removeById(@RequestParam("id") ID id) throws GeneralException;

    @PostMapping(value = {"/deleteById"})
    BaseResponseDTO deleteById(@RequestParam("id") ID id);

}
