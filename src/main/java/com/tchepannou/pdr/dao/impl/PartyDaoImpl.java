package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyDao;
import com.tchepannou.pdr.domain.Gender;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.domain.PartyKind;
import com.tchepannou.pdr.util.DateUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                domain.setBirthDate(DateUtils.asLocalDate(rs.getDate("birth_date")));
                domain.setHeigth(rs.getInt("height"));
                domain.setWeight(rs.getInt("weight"));
                domain.setGender(Gender.fromCode(rs.getString("gender")));
                domain.setKind(PartyKind.fromCode(rs.getString("kind")));
                return domain;
            }
        };
    }

}
