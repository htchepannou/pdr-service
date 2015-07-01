package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.ContactMechanismPurposeDao;
import com.tchepannou.pdr.domain.ContactMechanismPurpose;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ContactMechanismPurposeDaoImpl extends JdbcTemplate implements ContactMechanismPurposeDao {

    public ContactMechanismPurposeDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ContactMechanismPurpose findById(final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_contact_mechanism_purpose WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<ContactMechanismPurpose> findAll() {
        return query("SELECT * FROM t_contact_mechanism_purpose", getRowMapper());
    }

    //-- Private
    private RowMapper<ContactMechanismPurpose> getRowMapper () {
        return new RowMapper<ContactMechanismPurpose>() {
            @Override
            public ContactMechanismPurpose mapRow(final ResultSet rs, final int i) throws SQLException {
                final ContactMechanismPurpose purpose = new ContactMechanismPurpose();
                purpose.setId(rs.getLong("id"));
                purpose.setName(rs.getString("name"));
                return purpose;
            }
        };
    }
}
