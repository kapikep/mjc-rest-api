package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class MySQLGiftCertificateRepository implements GiftCertificateRepository {
    public static final String CREATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)" +
            " VALUES(?, ?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_GIFT_CERTIFICATE_HAS_TAG = "INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES (?, ?)";
    public static final String UPDATE = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=?" +
            " WHERE id=?";
    public static final String DELETE = "DELETE FROM gift_certificate WHERE id=?";
    public static final String DELETE_FROM_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id=?";
    private static final String READ_ALL = "SELECT * FROM gift_certificate gc LEFT OUTER JOIN gift_certificate_has_tag gcht on (gc.id = gcht.gift_certificate_id) " +
            "LEFT OUTER JOIN tag t ON (gcht.tag_id = t.id)";
    private static final String READ_ONE = READ_ALL + " WHERE gc.id=?";
    private static final String SEARCH_BY_NAME = READ_ALL + " WHERE gc.name=?";
    private static final List<String> SEARCH_CERTIFICATE_PARAM = Arrays.asList("name", "description");
    private static final List<String> SORT_PARAM = Arrays.asList("create_date", "last_update_date", "name", "description");

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
    public List<GiftCertificate> findGiftCertificate(Map<String, String> criteriaMap, String sorting) throws RepositoryException {
        List<GiftCertificate> giftCertificates;
        StringBuilder findQuery = new StringBuilder(READ_ALL);

        if(criteriaMap != null) {
            if (!criteriaMap.isEmpty()) {
                findQuery.append(" WHERE ");
            }
            Iterator<String> iter = criteriaMap.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                if (SEARCH_CERTIFICATE_PARAM.contains(s)) {
                    findQuery.append("gc.");
                }
                findQuery.append(s).append(" LIKE '%")
                        .append(criteriaMap.get(s)).append("%'");
                if (iter.hasNext()) {
                    findQuery.append(" AND ");
                }
            }
        }

        if(sorting != null){
            boolean desc = false;
            if(sorting.endsWith("desc")){
                desc = true;
                sorting = sorting.substring(0, sorting.length() - 5);
            }
            if (SORT_PARAM.contains(sorting)) {
                findQuery.append(" ORDER BY ");
                if(SEARCH_CERTIFICATE_PARAM.contains(sorting)){
                    findQuery.append("gc.");
                }
                findQuery.append(sorting);
                if(desc){
                    findQuery.append(" DESC");
                }
            }
        }

        try {
            giftCertificates = jdbcTemplate.query(findQuery.toString(), new GiftCertificateMapper());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    @Override
    public void createGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags) throws RepositoryException {
        try {
            jdbcTemplate.update(CREATE, giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                    giftCertificate.getDuration(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate());
            GiftCertificate giftCertificate1 = jdbcTemplate.queryForObject(SEARCH_BY_NAME, new GiftCertificateMapper(), giftCertificate.getName());
            tags.forEach(tag -> jdbcTemplate.update(INSERT_INTO_GIFT_CERTIFICATE_HAS_TAG,
                    giftCertificate1.getId(), tag.getId()));
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void updateGiftCertificate(GiftCertificate giftCertificate, List<Tag> tags) throws RepositoryException {
        try {
            jdbcTemplate.update(UPDATE, giftCertificate.getName(), giftCertificate.getDescription(), giftCertificate.getPrice(),
                    giftCertificate.getDuration(), giftCertificate.getCreateDate(), giftCertificate.getLastUpdateDate(), giftCertificate.getId());
            jdbcTemplate.update(DELETE_FROM_GIFT_CERTIFICATE_HAS_TAG, giftCertificate.getId());
            tags.forEach(tag -> jdbcTemplate.update(INSERT_INTO_GIFT_CERTIFICATE_HAS_TAG,
                    giftCertificate.getId(), tag.getId()));
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteGiftCertificate(int id) throws RepositoryException {
        try {
            jdbcTemplate.update(DELETE, id);
        } catch (DataAccessException e) {
            throw new RepositoryException(String.format("Requested resource not found (id = %d)", id), e);
        }
    }
}
