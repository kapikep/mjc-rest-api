package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.GiftCertificateMapper;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MySQLGiftCertificateRepository implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public MySQLGiftCertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> readAll() {
        List<GiftCertificate> giftCertificates;
        giftCertificates = jdbcTemplate.query("SELECT * FROM gift_certificate", new GiftCertificateMapper());

        System.out.println("readAll");
        return giftCertificates;

    }
}
