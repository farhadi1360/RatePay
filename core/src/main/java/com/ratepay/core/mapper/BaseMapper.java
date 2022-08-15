package com.ratepay.core.mapper;
/**
 * @author Mustafa Farhadi
 * @email  farhadi.kam@gmail.com
 */
import java.util.List;
import java.util.Set;

public interface BaseMapper<M,E> {

    M toModel(final E entity);

    List<M> toModel(final List<E> entities);

//    Set<M> toModel(Set<E> entities);

    E toEntity(final M model);

    List<E> toEntity(final List<M> models);
}