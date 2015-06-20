package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.domain.Domain;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DomainDaoImpl extends JdbcTemplate implements DomainDao{

    public DomainDaoImpl (final DataSource ds) {
        super (ds);
    }

    @Override
    public Domain findById (final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_domain WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<Domain> findAll() {
        return query(
                "SELECT * FROM t_domain",
                getRowMapper()
        );
    }

    private RowMapper<Domain> getRowMapper (){
        return new RowMapper() {
            @Override
            public Domain mapRow(final ResultSet rs, final int i) throws SQLException {
                final Domain domain = new Domain ();
                domain.setId(rs.getLong("id"));
                domain.setName(rs.getString("name"));
                domain.setDescription(rs.getString("description"));
                return domain;
            }
        };
    }
}
