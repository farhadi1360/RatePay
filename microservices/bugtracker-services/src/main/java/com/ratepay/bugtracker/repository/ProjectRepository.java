package com.ratepay.bugtracker.repository;
/**
 * Created by Mostafa.Farhadi Email : farhadi.kam@gmail.com.
 */
import com.ratepay.client.bugtracker.entities.Project;
import com.ratepay.core.repository.BaseSQLRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends BaseSQLRepository<Project, Long> {
    Optional<Project> findByCode(String code);
}
