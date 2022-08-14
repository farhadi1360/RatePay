package com.ratepay.core.rest.impl;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ratepay.core.dto.BaseResponseDTO;
import com.ratepay.core.dto.PageAbleDto;
import com.ratepay.core.dto.PageDto;
import com.ratepay.core.rest.BaseRestSqlModeAPI;
import com.ratepay.core.service.MainServiceSQLMode;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;


public abstract class BaseRestSqlModeImpl<M, ID extends Serializable>  implements BaseRestSqlModeAPI<M,ID> {
    protected Logger logger = LoggerFactory.getLogger(BaseRestSqlModeImpl.class);
    protected MainServiceSQLMode<M, ID> service;
    protected ObjectMapper objectMapper;

    public BaseRestSqlModeImpl(MainServiceSQLMode<M, ID> service) {
        this.service = service;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    @SneakyThrows
    public BaseResponseDTO<M> save(M model) {
        Optional<M> result = service.save(model);
        result.ifPresent(out -> logger.info("data was successful created "));
        return BaseResponseDTO.ok(result.get());
    }

    @SneakyThrows
    public BaseResponseDTO<M> update(@RequestBody @Valid M model) {
        Optional<M> result = service.save(model);
        result.ifPresent(out -> logger.info("model was successful updated "));
        return BaseResponseDTO.ok(result.get());
    }

    @Override
    @SneakyThrows
    public BaseResponseDTO<M> findByUniqueId(ID id) {
        Optional<M> result = service.findById(id);

        return BaseResponseDTO.ok(result.get());
    }

    @Override
    @SneakyThrows
    public BaseResponseDTO<PageAbleDto<M>> getAllByPaging(PageDto dto) {
        logger.info("call api for get all data");
        Optional<PageAbleDto<M>> result = service.getAllByPaging(dto);
        result.ifPresent(out -> logger.info("list size is : ,{}", result.get().getContentList()));
        return BaseResponseDTO.ok(result.get());
    }

    @Override
    @SneakyThrows
    public BaseResponseDTO<List<M>> getAll() {
        logger.info("call api for get all data");
        Optional<List<M>> result = service.getAll();
        result.ifPresent(out -> logger.info("list size is : ,{}", result.get().size()));
        return BaseResponseDTO.ok(result.get());
    }

    @Override
    @SneakyThrows
    public BaseResponseDTO removeById(ID id) {
        service.deleteById(id);
        return BaseResponseDTO.ok(null);
    }
    @Override
    public BaseResponseDTO deleteById(ID id) {
        try {
            service.deleteById(id);
            return  BaseResponseDTO.ok("Deleted was success");
        } catch (Exception e) {
            logger.error(format("delete {} by id {} throw exception", id), e);
            return  BaseResponseDTO.ok("");
        }
    }
}
