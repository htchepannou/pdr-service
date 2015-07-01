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
        return new RowMapper<Party>() {
            @Override
            public Party mapRow(final ResultSet rs, final int i) throws SQLException {
                final Party obj = new Party ();
                obj.setId(rs.getLong("id"));

                obj.setDeleted(rs.getBoolean("deleted"));
                obj.setFromDate(rs.getTimestamp("from_date"));
                obj.setToDate(rs.getTimestamp("to_date"));

                obj.setName(rs.getString("name"));
                obj.setFirstName(rs.getString("first_name"));
                obj.setLastName(rs.getString("last_name"));
                obj.setPrefix(rs.getString("prefix"));
                obj.setSuffix(rs.getString("suffix"));
                obj.setBirthDate(rs.getDate("birth_date"));
                obj.setHeigth(rs.getInt("height"));
                obj.setWeight(rs.getInt("weight"));
                obj.setGender(Gender.fromText(rs.getString("gender")));
                obj.setKind(PartyKind.fromText(rs.getString("kind")));
                return obj;
            }
        };
    }
}
