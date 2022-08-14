package com.ratepay.core.repository;

import com.ratepay.core.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface BaseSQLRepository<T extends BaseEntity<ID>,ID extends Serializable> extends JpaRepository<T, ID>,QuerydslPredicateExecutor<T> {
}
