package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.RoleDao;
import com.tchepannou.pdr.domain.Role;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleDaoImpl extends JdbcTemplate implements RoleDao {

    public RoleDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Role findById(long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_role WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<Role> findAll() {
        return query("SELECT * FROM t_role", getRowMapper());
    }

    //-- Private
    private RowMapper<Role> getRowMapper () {
        return new RowMapper<Role>() {
            @Override
            public Role mapRow(final ResultSet rs, final int i) throws SQLException {
                final Role role = new Role();
                role.setId(rs.getLong("id"));
                role.setName(rs.getString("name"));
                return role;
            }
        };
    }
}
