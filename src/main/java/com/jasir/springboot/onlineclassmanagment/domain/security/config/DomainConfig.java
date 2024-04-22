package com.jasir.springboot.onlineclassmanagment.domain.security.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.jasir.springboot.onlineclassmanagment.domain")
@EnableJpaRepositories("com.jasir.springboot.onlineclassmanagment.domain")
@EnableTransactionManagement
public class DomainConfig {
}
