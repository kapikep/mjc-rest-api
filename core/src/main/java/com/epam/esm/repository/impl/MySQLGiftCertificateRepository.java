package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public class MySQLGiftCertificateRepository implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public MySQLGiftCertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> readAll() throws RepositoryException{
        List<GiftCertificate> giftCertificates;

        giftCertificates = jdbcTemplate.query("SELECT * FROM gift_certificate", new GiftCertificateMapper());

        return giftCertificates;
    }

    @Override
    public GiftCertificate readGiftCertificate(int id) throws RepositoryException {
        GiftCertificate giftCertificate = null;
        try {
            giftCertificate = jdbcTemplate.queryForObject("SELECT * FROM gift_certificate WHERE id=?", new GiftCertificateMapper(), id);
        }catch (DataAccessException e){
            System.out.println("Rep ->" + e);
            throw new RepositoryException(String.format("Requested resource not found (id = %d)", id), e);
        }
        return giftCertificate;
    }
}
