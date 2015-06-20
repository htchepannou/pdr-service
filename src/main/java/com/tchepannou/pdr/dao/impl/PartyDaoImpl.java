package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyDao;
import com.tchepannou.pdr.domain.Gender;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyKind;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class PartyDaoImpl extends JdbcTemplate implements PartyDao {
    public PartyDaoImpl (final DataSource ds) {
        super (ds);
    }

    @Override
    public Party findById (final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_party WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    private RowMapper<Party> getRowMapper (){
        return new RowMapper() {
            @Override
            public Party mapRow(final ResultSet rs, final int i) throws SQLException {
                final Party domain = new Party ();
                domain.setId(rs.getLong("id"));
                domain.setName(rs.getString("name"));
                domain.setFirstName(rs.getString("first_name"));
                domain.setLastName(rs.getString("last_name"));
                domain.setPrefix(rs.getString("prefix"));
                domain.setSuffix(rs.getString("suffix"));
                domain.setBirthDate(asLocalDate(rs.getDate("birth_date")));
                domain.setHeigth(rs.getInt("height"));
                domain.setWeight(rs.getInt("weight"));
                domain.setGender(Gender.fromCode(rs.getString("gender")));
                domain.setKind(PartyKind.fromCode(rs.getString("kind")));
                return domain;
            }
        };
    }

    public static LocalDate asLocalDate(java.util.Date date) {
        return asLocalDate(date, ZoneId.systemDefault());
    }

    public static LocalDate asLocalDate(java.util.Date date, ZoneId zone) {
        if (date == null)
            return null;

        if (date instanceof java.sql.Date)
            return ((java.sql.Date) date).toLocalDate();
        else
            return Instant.ofEpochMilli(date.getTime()).atZone(zone).toLocalDate();
    }

}
