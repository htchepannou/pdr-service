package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.domain.UserStatus;
import com.tchepannou.pdr.util.DateUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

public class UserDaoImpl extends JdbcTemplate implements UserDao {
    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public User findById(final long id) {
        try {
            return queryForObject(
                    "SELECT U.* FROM t_user U JOIN t_party P ON U.party_fk=P.id WHERE U.id=? AND P.deleted=?",
                    new Object[]{id, false},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public User findByParty(final long partyId) {
        try {
            return queryForObject(
                    "SELECT U.* FROM t_user U JOIN t_party P ON U.party_fk=P.id WHERE P.id=? AND P.deleted=?",
                    new Object[]{partyId},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public long create(final User user) {
        KeyHolder holder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final String sql = "INSERT INTO t_user(party_fk, login, password, status, from_date, deleted) VALUES(?,?,?,?,?,?)";
                final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                final UserStatus status = user.getStatus();

                ps.setLong(1, user.getPartyId());
                ps.setString(2, user.getLogin());
                ps.setString(3, user.getPassword());
                ps.setString(4, status != null ? String.valueOf(status.getCode()) : null);
                ps.setTimestamp(5, DateUtils.asTimestamp(user.getFromDate()));
                ps.setBoolean(6, false);
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void update(final User user) {
        final String sql = "UPDATE t_user SET login=?, password=?, status=? WHERE id=?";
        update(sql,
                user.getLogin(),
                user.getPartyId(),
                String.valueOf(user.getStatus().getCode()),
                user.getId()
        );
    }

    @Override
    public void delete(final long id) {
        final String sql = "UPDATE t_user SET deleted=?, login=?, to_date=? WHERE id=?";
        update(sql,
                true,
                UUID.randomUUID().toString(),
                new java.util.Date (),
                id
        );
    }

    private RowMapper<User> getRowMapper (){
        return new RowMapper<User>() {
            @Override
            public User mapRow(final ResultSet rs, final int i) throws SQLException {
                final User obj = new User ();
                obj.setId(rs.getLong("id"));
                obj.setPartyId(rs.getLong("party_fk"));

                obj.setDeleted(rs.getBoolean("deleted"));
                obj.setFromDate(rs.getTimestamp("from_date"));
                obj.setToDate(rs.getTimestamp("to_date"));

                obj.setLogin(rs.getString("login"));
                obj.setPassword(rs.getString("password"));
                obj.setStatus(UserStatus.fromText(rs.getString("status")));
                return obj;
            }
        };
    }
}
