package com.epam.esm.repository.impl;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;

class MySQLGiftCertificateRepositoryTest {

    private ApplicationContext applicationContext;
    private GiftCertificateRepository giftCertificateRepository;

    @BeforeEach
    public void init() {
        this.applicationContext = new AnnotationConfigApplicationContext(CoreConfig.class);
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
        System.out.println(applicationContext.getBean("jdbcTemplate", JdbcTemplate.class));
        this.giftCertificateRepository = applicationContext.getBean(MySQLGiftCertificateRepository.class);
    }

    @Test
    void readAll() {
        System.out.println(giftCertificateRepository);
    }
}