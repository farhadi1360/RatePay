package com.ratepay.core.service.impl;

import com.querydsl.core.types.Predicate;
import com.ratepay.core.dto.BaseDto;
import com.ratepay.core.dto.PageAbleDto;
import com.ratepay.core.dto.PageDto;
import com.ratepay.core.entity.BaseEntity;
import com.ratepay.core.exception.BaseException;
import com.ratepay.core.exception.CoreExceptionTypes;
import com.ratepay.core.exception.CreatingCoreException;
import com.ratepay.core.exception.GeneralException;
import com.ratepay.core.mapper.BaseMapper;
import com.ratepay.core.repository.BaseSQLRepository;
import com.ratepay.core.service.MainServiceSQLMode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
@Transactional
@Log4j
public abstract class MainServiceSQLModeImpl <M, E extends BaseEntity<ID>, ID extends Serializable> implements MainServiceSQLMode<M, ID> {
    public BaseMapper<M, E> mapper;
    public BaseSQLRepository<E, ID> repository;
    final static Logger logger = LoggerFactory.getLogger(MainServiceSQLModeImpl.class);


    public MainServiceSQLModeImpl(BaseMapper<M, E> mapper, BaseSQLRepository<E, ID> repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public abstract Predicate queryBuilder(M filter);

    @Override
    @Transactional(readOnly = true)
    public PageDto findAllTable(M filter, Pageable pageable) {
        Predicate predicate = queryBuilder(filter);
        Page<E> page = repository.findAll(predicate, pageable);
        return new PageDto(page.getTotalElements(), repository.count(predicate), mapper.toModel(page.getContent()));
    }

    @Override
    @Transactional(readOnly = true)
    public PageDto findAllSelect(M filter, Pageable pageable) {
        Predicate predicate = queryBuilder(filter);
        Page<E> page = repository.findAll(predicate, pageable);
        return new PageDto(page.getTotalElements(), repository.count(predicate), page.getContent().stream().
                map(m -> new BaseDto(m.getId().toString(), m.getSelectTitle())));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<M>> findAll(M filter) throws BaseException {
        Iterable<E> list = repository.findAll(queryBuilder(filter));
        if (list!=null && list.iterator().hasNext()){
            List<E> result = new ArrayList<>();
            list.forEach(result::add);
            return Optional.of(mapper.toModel(result));
        }else{
            logger.info("no any data was found ");
            CreatingCoreException.Result ex = CreatingCoreException.getExceptionSpecification(CoreExceptionTypes.NOT_FOUND_EXCEPTION);
            throw new GeneralException(ex.getCode(), ex.getMessage(), null, ex.getHttpStatusCode());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Long countAll(M filter) {
        return repository.count(queryBuilder(filter));
    }

    @Override
    public Optional<M> findById(ID id) {
        Optional<E> result = repository.findById(id);
        if (result.isPresent()) {
            return Optional.of(mapper.toModel(result.get()));
        } else {
            logger.info("Data Not Found By Id ,{}", id);
            CreatingCoreException.Result ex = CreatingCoreException.getExceptionSpecification(CoreExceptionTypes.NOT_FOUND_EXCEPTION);
            throw new GeneralException(ex.getCode(), ex.getMessage(), null, ex.getHttpStatusCode());
        }
    }

    @Override
    public Optional<M>  save(M model) {
        try {
            Optional<M> result = Optional.of(mapper.toModel(repository.save(mapper.toEntity(model))));

            return result;
        }catch (Exception e){
            logger.error("error was occurred in SaveOrUpdate method  because : {}", e.getMessage());
            CreatingCoreException.Result ex = CreatingCoreException.getExceptionSpecification(CoreExceptionTypes.UNKNOWN_INTERNAL_ERROR);
            throw new GeneralException(ex.getCode(), ex.getMessage(), null, ex.getHttpStatusCode());
        }

    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public Optional<List<M>> getAll() throws GeneralException {
        List<M> list = mapper.toModel(repository.findAll());
        if (list!=null && list.size()>0){
            return Optional.of(list);
        }else{
            logger.info("no any data was found ");
            CreatingCoreException.Result ex = CreatingCoreException.getExceptionSpecification(CoreExceptionTypes.NOT_FOUND_EXCEPTION);
            throw new GeneralException(ex.getCode(), ex.getMessage(), null, ex.getHttpStatusCode());
        }
    }

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public Optional<PageAbleDto<M>> getAllByPaging(PageDto pageDto) throws GeneralException {
        PageAbleDto<M> out = new PageAbleDto<>();
        Page<E> pages = repository.findAll(PageRequest.of(pageDto.getPageNumber(), pageDto.getPageSize(), pageDto.getDirection(), "createdBy"));
        if(pages!=null && pages.getContent().size()>0){
            List<M> list = mapper.toModel(pages.getContent());
            out.setContentList(list);
            out.getPage().setPageNumber(pageDto.getPageNumber());
            out.getPage().setPageSize(pageDto.getPageSize());
            out.getPage().setDirection(Sort.Direction.DESC);
            out.getPage().setSortBy("create");
            out.getPage().setTotalElement(pages.getTotalElements());
            return Optional.of(out);

        } else {
            logger.info("no any data was found ");
            CreatingCoreException.Result ex = CreatingCoreException.getExceptionSpecification(CoreExceptionTypes.NOT_FOUND_EXCEPTION);
            throw new GeneralException(ex.getCode(), ex.getMessage(), null, ex.getHttpStatusCode());
        }
    }

}

