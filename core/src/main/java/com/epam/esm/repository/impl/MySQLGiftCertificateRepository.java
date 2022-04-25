package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySQLGiftCertificateRepository implements GiftCertificateRepository {

    private final JdbcTemplate jdbcTemplate;

    public MySQLGiftCertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<GiftCertificate> readAll() throws RepositoryException {
        List<GiftCertificate> giftCertificates;
        try {
            giftCertificates = jdbcTemplate.query("SELECT * FROM gift_certificate", new GiftCertificateMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    @Override
    public GiftCertificate readGiftCertificate(int id) throws RepositoryException {
        GiftCertificate giftCertificate = null;
        try {
            giftCertificate = jdbcTemplate.queryForObject("SELECT * FROM gift_certificate WHERE id=?", new GiftCertificateMapper(), id);
        } catch (DataAccessException e) {
            throw new RepositoryException(String.format("Requested resource not found (id = %d)", id), e);
        }
        return giftCertificate;
    }

    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate) throws RepositoryException {
        try {
            jdbcTemplate.update("INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)" +
                            " VALUES(?, ?, ?, ?, ?, ?)", giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                    giftCertificate.getDuration(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate) throws RepositoryException {
        try {
            jdbcTemplate.update("UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=?" +
                            " WHERE id=?", giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                    giftCertificate.getDuration(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(), giftCertificate.getId());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteGiftCertificate(int id) throws RepositoryException {
        try {
            jdbcTemplate.update("DELETE * FROM gift_certificate WHERE id=?", id);
        } catch (DataAccessException e) {
            throw new RepositoryException(String.format("Requested resource not found (id = %d)", id), e);
        }
    }
}
