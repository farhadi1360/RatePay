package com.ratepay.bugtracker.config;
/**
 * Copyright (c) 2019 , Fanap  Project . All Rights Reserved
 * Created by Mostafa.Farhadi on 3/3/2019.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableJpaRepositories("com.ratepay.bugtracker.repository")
@EntityScan(basePackages = {"com.ratepay.client.bugtracker.entities"})
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

}
