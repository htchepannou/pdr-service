package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.DomainUserDao;
import com.tchepannou.pdr.domain.DomainUser;
import com.tchepannou.pdr.util.DateUtils;
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

public class DomainUserDaoImpl extends JdbcTemplate implements DomainUserDao {
    public DomainUserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public DomainUser findByDomainByUser(final long domainId, final long userId) {
        try {
            return queryForObject(
                    "SELECT * FROM t_domain_user WHERE domain_fk=? AND user_fk=?",
                    new Object[]{domainId, userId},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public long create(final DomainUser domainUser) {
        KeyHolder holder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final String sql = "INSERT INTO t_domain_user(domain_fk, user_fk, from_date) VALUES(?,?,?)";
                final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                ps.setLong(1, domainUser.getDomainId());
                ps.setLong(2, domainUser.getUserId());
                ps.setTimestamp(3, DateUtils.asTimestamp(domainUser.getFromDate()));
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void delete(final long id) {
        update("DELETE FROM t_domain_user WHERE id=?", new Object[] {id});
    }

    //-- Private
    private RowMapper<DomainUser> getRowMapper (){
        return new RowMapper() {
            @Override
            public DomainUser mapRow(final ResultSet rs, final int i) throws SQLException {
                final DomainUser obj = new DomainUser ();
                obj.setId(rs.getLong("id"));

                obj.setDomainId(rs.getLong("domain_fk"));
                obj.setUserId(rs.getLong("user_fk"));

                obj.setFromDate(DateUtils.asLocalDateTime(rs.getTimestamp("from_date")));
                return obj;
            }
        };
    }

}
