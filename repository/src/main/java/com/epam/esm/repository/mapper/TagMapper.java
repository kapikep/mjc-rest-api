package com.epam.esm.repository.mapper;

import com.epam.esm.entity.TagEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * Tag mapper for JdbcTemplate
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class TagMapper implements RowMapper<TagEntity> {
    /**
     * Implementations must implement this method to map each row of data
     * in the ResultSet. This method should not call {@code next()} on
     * the ResultSet; it is only supposed to map values of the current row.
     * @param rs the ResultSet to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     * @return the result object for the current row (may be {@code null})
     * @throws SQLException if an SQLException is encountered getting
     * column values (that is, there's no need to catch SQLException)
     */
    @Override
    public TagEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        TagEntity tag = new TagEntity();
        tag.setId(rs.getInt("id"));
        tag.setName(rs.getString("name"));
        return tag;
    }
}
