package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.domain.Domain;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DomainDaoImpl extends JdbcTemplate implements DomainDao{
    //-- Constructor
    public DomainDaoImpl (final DataSource ds) {
        super (ds);
    }

    //-- DomainDao overrides
    @Override
    public Domain findById (final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_domain WHERE id=? AND deleted=?",
                    new Object[]{id, false},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<Domain> findAll() {
        return query(
                "SELECT * FROM t_domain WHERE deleted=?",
                new Object[]{false},
                getRowMapper()
        );
    }

    @Override
    public long create(final Domain domain) {
        KeyHolder holder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
                   @Override
                   public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                       final String sql = "INSERT INTO t_domain(name, description, deleted) VALUES(?,?,?)";
                       final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                       ps.setString(1, domain.getName());
                       ps.setString(2, domain.getDescription());
                       ps.setBoolean(3, domain.isDeleted());
                       return ps;
                   }
               }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void update(final Domain domain) {
        final String sql = "UPDATE t_domain SET name=?, description=? WHERE id=?";
        update(sql, new Object[]{
                domain.getName(),
                domain.getDescription(),
                domain.getId()
        });
    }

    @Override
    public void delete(final long id) {
        String sql = "UPDATE t_domain SET deleted=? WHERE id=?";
        update(sql, new Object[]{
                true,
                id
        });
    }

    //-- Private
    private RowMapper<Domain> getRowMapper (){
        return new RowMapper() {
            @Override
            public Domain mapRow(final ResultSet rs, final int i) throws SQLException {
                final Domain domain = new Domain ();
                domain.setId(rs.getLong("id"));
                domain.setName(rs.getString("name"));
                domain.setDescription(rs.getString("description"));
                domain.setDeleted(rs.getBoolean("deleted"));
                return domain;
            }
        };
    }
}
