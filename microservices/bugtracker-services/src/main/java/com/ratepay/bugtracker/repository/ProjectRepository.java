package com.ratepay.bugtracker.repository;

import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.core.repository.BaseSQLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends BaseSQLRepository<Project, Long> {
    Optional<Project> findByCode(String code);
}
