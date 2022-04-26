package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MySQLGiftCertificateRepository implements GiftCertificateRepository {
    private final String READ_ALL = "SELECT * FROM gift_certificate gc LEFT OUTER JOIN gift_certificate_has_tag gcht on (gc.id = gcht.gift_certificate_id) LEFT OUTER JOIN tag t ON (gcht.tag_id = t.id)";
    private final String READ_ONE = READ_ALL + " WHERE gc.id=?";

    private final JdbcTemplate jdbcTemplate;
    private final MySQLTagRepository tagRepository;

    public MySQLGiftCertificateRepository(JdbcTemplate jdbcTemplate, MySQLTagRepository tagRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<GiftCertificate> readAllGiftCertificates() throws RepositoryException {
        List<GiftCertificate> giftCertificates;
        try {
            giftCertificates = jdbcTemplate.query(READ_ALL, new GiftCertificateMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> readGiftCertificate(int id) throws RepositoryException {
        List<GiftCertificate> giftCertificates;
        try {
            giftCertificates = jdbcTemplate.query(READ_ONE, new GiftCertificateMapper(), id);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags) throws RepositoryException {
        try {
            jdbcTemplate.update("INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)" +
                            " VALUES(?, ?, ?, ?, ?, ?)", giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                    giftCertificate.getDuration(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate());
            tags.forEach(tag -> {
                jdbcTemplate.update("INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (?, ?)", giftCertificate.getId(), tag.getId());
            });
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags) throws RepositoryException {
        try {
            jdbcTemplate.update("UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=?" +
                            " WHERE id=?", giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                    giftCertificate.getDuration(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(), giftCertificate.getId());
            jdbcTemplate.update("DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id=?", giftCertificate.getId());
            tags.forEach(tag -> {
                jdbcTemplate.update("INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (?, ?)", giftCertificate.getId(), tag.getId());
            });
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteGiftCertificate(int id) throws RepositoryException {
        try {
            jdbcTemplate.update("DELETE FROM gift_certificate WHERE id=?", id);
        } catch (DataAccessException e) {
            throw new RepositoryException(String.format("Requested resource not found (id = %d)", id), e);
        }
    }
}
