package com.epam.esm.repository.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GiftCertificateResultSetExtractor implements ResultSetExtractor<List<GiftCertificate>> {
    @Override
    public List<GiftCertificate> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, GiftCertificate> giftCertificateById = new LinkedHashMap<>();
        GiftCertificate giftCertificate;
        while (rs.next()) {
            int id = rs.getInt("id");
            if (!giftCertificateById.containsKey(id)) {
                giftCertificate = new GiftCertificateMapper().mapRow(rs, 0);
                giftCertificateById.put(id, giftCertificate);
            } else {
                giftCertificate = giftCertificateById.get(id);
            }
            giftCertificate.addTag(new Tag(rs.getInt("tag.id"), rs.getString("tag.name")));
        }
        return new ArrayList<>(giftCertificateById.values());
    }
}