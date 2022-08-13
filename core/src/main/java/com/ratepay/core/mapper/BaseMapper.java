package com.ratepay.core.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import java.util.List;

public interface BaseMapper<M,E> {

    M toModel(final E entity);

    List<M> toModel(final List<E> entities);

    E toEntity(final M model);

    List<E> toEntity(final List<M> models);
}