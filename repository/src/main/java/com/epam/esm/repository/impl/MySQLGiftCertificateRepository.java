package com.epam.esm.repository.impl;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.repository.constant.GiftCertificateSearchParam;
import com.epam.esm.repository.exception.RepositoryException;
import com.epam.esm.repository.mapper.GiftCertificateMapper;
import com.epam.esm.repository.interf.GiftCertificateRepository;
import com.epam.esm.repository.mapper.GiftCertificateResultSetExtractor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.*;

/**
 * MySQL repository for gift certificates
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Repository
public class MySQLGiftCertificateRepository implements GiftCertificateRepository {
    public static final String CREATE = "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)" +
            " VALUES(?, ?, ?, ?, ?, ?)";
    public static final String INSERT_INTO_GIFT_CERTIFICATE_HAS_TAG = "INSERT INTO gift_certificate_has_tag (gift_certificate_id, tag_id) VALUES";
    public static final String UPDATE = "UPDATE gift_certificate SET name=?, description=?, price=?, duration=?, create_date=?, last_update_date=?" +
            " WHERE id=?";
    public static final String DELETE = "DELETE FROM gift_certificate WHERE id=?";
    public static final String DELETE_FROM_GIFT_CERTIFICATE_HAS_TAG = "DELETE FROM gift_certificate_has_tag WHERE gift_certificate_id=?";
    private static final String READ_ALL = "SELECT * FROM gift_certificate gc LEFT OUTER JOIN gift_certificate_has_tag gcht on (gc.id = gcht.gift_certificate_id) " +
            "LEFT OUTER JOIN tag tag ON (gcht.tag_id = tag.id)";
    private static final String READ_ONE = READ_ALL + " WHERE gc.id=?";

    private final JdbcTemplate jdbcTemplate;

    public MySQLGiftCertificateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Reads all gift certificates from database
     *
     * @return list with all GiftCertificateEntity
     */
    @Override
    public List<GiftCertificateEntity> readAllGiftCertificates() throws RepositoryException {
        List<GiftCertificateEntity> giftCertificates;

        try {
            giftCertificates = jdbcTemplate.query(READ_ALL, new GiftCertificateResultSetExtractor());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    /**
     * Reads gift certificate by id from database
     *
     * @param id
     * @return list with gift GiftCertificateEntity
     */
    @Override
    public GiftCertificateEntity readGiftCertificate(int id) throws RepositoryException {
        GiftCertificateEntity giftCertificate;

        try {
            giftCertificate = jdbcTemplate.query(READ_ONE, rs -> {
                GiftCertificateEntity giftCertificate1;
                rs.next();
                giftCertificate1 = new GiftCertificateMapper().mapRow(rs, 0);
                do {
                    giftCertificate1.addTag(new TagEntity(rs.getInt("tag.id"), rs.getString("tag.name")));
                } while (rs.next());
                return giftCertificate1;
            }, id);

        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return giftCertificate;
    }

    /**
     * Finds gift certificates by criteria map and sorted by sorting param from database
     *
     * @param criteriaMap search parameters
     * @param sorting sorting for the result
     * @return list with GiftCertificateEntity
     */
    @Override
    public List<GiftCertificateEntity> findGiftCertificate(Map<String, String> criteriaMap, String sorting) throws RepositoryException {
        List<GiftCertificateEntity> giftCertificates;
        StringBuilder findQuery = new StringBuilder(READ_ALL);

        if (criteriaMap != null) {
            if (!criteriaMap.isEmpty()) {
                findQuery.append(" WHERE ");
            }
            Iterator<String> iter = criteriaMap.keySet().iterator();
            while (iter.hasNext()) {
                String s = iter.next();
                if (GiftCertificateSearchParam.SEARCH_CERTIFICATE_PARAM.contains(s)) {
                    findQuery.append("gc.");
                }
                findQuery.append(s).append(" LIKE '%")
                        .append(criteriaMap.get(s)).append("%'");
                if (iter.hasNext()) {
                    findQuery.append(" AND ");
                }
            }
        }

        if (sorting != null) {
            boolean desc = false;
            if (sorting.startsWith("-")) {
                desc = true;
                sorting = sorting.substring(1);
            }
            if (sorting.startsWith("+") || sorting.startsWith(" ")) {
                sorting = sorting.substring(1);
            }
            if (GiftCertificateSearchParam.SORT_PARAM.contains(sorting)) {
                findQuery.append(" ORDER BY ");
                if (GiftCertificateSearchParam.SEARCH_CERTIFICATE_PARAM.contains(sorting)) {
                    findQuery.append("gc.");
                }
                findQuery.append(sorting);
                if (desc) {
                    findQuery.append(" DESC");
                }
            }
        }

        try {
            giftCertificates = jdbcTemplate.query(findQuery.toString(), new GiftCertificateResultSetExtractor());
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return giftCertificates;
    }

    /**
     * Creates new gift certificate in database
     *
     * @param certificate gift certificate entity to create in db, excluding tag field
     * @return id for created gift certificate
     */
    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public int createGiftCertificate(GiftCertificateEntity certificate) throws RepositoryException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(con -> {
                PreparedStatement ps = con.prepareStatement(CREATE, new String[]{"id"});
                ps.setString(1, certificate.getName());
                ps.setString(2, certificate.getDescription());
                ps.setDouble(3, certificate.getPrice());
                ps.setInt(4, certificate.getDuration());
                ps.setTimestamp(5, Timestamp.valueOf(certificate.getCreateDate()));
                ps.setTimestamp(6, Timestamp.valueOf(certificate.getLastUpdateDate()));
                return ps;
            }, keyHolder);

            List<TagEntity> tags = certificate.getTags();

            insertIntoGiftCertificateHasTag(keyHolder.getKey(), tags);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
        return keyHolder.getKey().intValue();
    }

    private void insertIntoGiftCertificateHasTag(Number giftId, List<TagEntity> tags) {
        StringBuilder builder;
        if (tags != null && !tags.isEmpty()) {
            builder = new StringBuilder(INSERT_INTO_GIFT_CERTIFICATE_HAS_TAG);
            Iterator<TagEntity> iter = tags.iterator();
            while (iter.hasNext()) {
                builder.append(" (").append(giftId).append(",")
                        .append(iter.next().getId()).append(")");
                if (iter.hasNext()) {
                    builder.append(",");
                }
            }
            jdbcTemplate.execute(builder.toString());
        }
    }

    /**
     * Updates new gift certificate
     *
     * @param certificate gift certificate entity to update in db, excluding tag field
     */
    @Override
    @Transactional(rollbackFor = RepositoryException.class)
    public void updateGiftCertificate(GiftCertificateEntity certificate) throws RepositoryException {
        try {
            jdbcTemplate.update(UPDATE, certificate.getName(), certificate.getDescription(), certificate.getPrice(),
                    certificate.getDuration(), certificate.getCreateDate(), certificate.getLastUpdateDate(), certificate.getId());
            jdbcTemplate.update(DELETE_FROM_GIFT_CERTIFICATE_HAS_TAG, certificate.getId());

            List<TagEntity> tags = certificate.getTags();
            insertIntoGiftCertificateHasTag(certificate.getId(), tags);
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }

    /**
     * Delete gift certificate by id from database
     */
    @Override
    public void deleteGiftCertificate(int id) throws RepositoryException {
        int res;
        try {
            res = jdbcTemplate.update(DELETE, id);
            if (res == 0) {
                throw new RepositoryException("Resource to delete not found");
            }
        } catch (DataAccessException e) {
            throw new RepositoryException(e.getMessage(), e);
        }
    }
}
