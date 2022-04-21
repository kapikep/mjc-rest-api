package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MySQLGiftCertificateRepository implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public MySQLGiftCertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void readAll() {

    }
}
