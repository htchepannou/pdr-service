package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.ContactMechanismTypeDao;
import com.tchepannou.pdr.domain.ContactMechanismType;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactMechanismTypeDaoImpl extends JdbcTemplate implements ContactMechanismTypeDao {

    public ContactMechanismTypeDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ContactMechanismType findById(final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_contact_mechanism_type WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<ContactMechanismType> findAll() {
        return query("SELECT * FROM t_contact_mechanism_type", getRowMapper());
    }

    //-- Private
    private RowMapper<ContactMechanismType> getRowMapper () {
        return new RowMapper<ContactMechanismType>() {
            @Override
            public ContactMechanismType mapRow(final ResultSet rs, final int i) throws SQLException {
                final ContactMechanismType type = new ContactMechanismType();
                type.setId(rs.getLong("id"));
                type.setName(rs.getString("name"));
                return type;
            }
        };
    }
}
