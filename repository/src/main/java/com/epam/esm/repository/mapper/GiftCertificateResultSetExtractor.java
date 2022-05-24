package com.epam.esm.repository.mapper;

import com.epam.esm.entity.GiftCertificateEntity;
import com.epam.esm.entity.TagEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GiftCertificateResultSetExtractor implements ResultSetExtractor<List<GiftCertificateEntity>> {
    @Override
    public List<GiftCertificateEntity> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, GiftCertificateEntity> giftCertificateById = new LinkedHashMap<>();
        GiftCertificateEntity giftCertificate;
        while (rs.next()) {
            int id = rs.getInt("id");
            if (!giftCertificateById.containsKey(id)) {
                giftCertificate = new GiftCertificateMapper().mapRow(rs, 0);
                giftCertificateById.put(id, giftCertificate);
            } else {
                giftCertificate = giftCertificateById.get(id);
            }
            giftCertificate.addTag(new TagEntity(rs.getInt("tag.id"), rs.getString("tag.name")));
        }
        return new ArrayList<>(giftCertificateById.values());
    }
}