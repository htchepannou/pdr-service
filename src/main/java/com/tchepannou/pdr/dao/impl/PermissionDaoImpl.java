package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PermissionDao;
import com.tchepannou.pdr.domain.Permission;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PermissionDaoImpl extends JdbcTemplate implements PermissionDao {

    public PermissionDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Permission> findByRole(final long roleId) {
        return query("SELECT P.* FROM t_permission P JOIN t_role_permission R ON P.id=R.permission_fk", getRowMapper());
    }

    //-- Private
    private RowMapper<Permission> getRowMapper () {
        return new RowMapper<Permission>() {
            @Override
            public Permission mapRow(final ResultSet rs, final int i) throws SQLException {
                final Permission permission = new Permission();
                permission.setId(rs.getLong("id"));
                permission.setName(rs.getString("name"));
                return permission;
            }
        };
    }
}
