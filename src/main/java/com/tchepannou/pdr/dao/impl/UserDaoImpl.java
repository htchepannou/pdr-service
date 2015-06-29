package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.domain.*;
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
import java.util.UUID;

public class UserDaoImpl extends JdbcTemplate implements UserDao {
    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public User findById(long id) {
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
    public User findByParty(long partyId) {
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
                final String sql = "INSERT INTO t_user(party_fk, login, password, status, from_date, to_date, deleted) VALUES(?,?,?,?,?,?,?)";
                final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                final UserStatus status = user.getStatus();

                ps.setLong(1, user.getPartyId());
                ps.setString(2, user.getLogin());
                ps.setString(3, user.getPassword());
                ps.setString(4, status != null ? String.valueOf(status.getCode()) : null);
                ps.setTimestamp(5, DateUtils.asTimestamp(user.getFromDate()));
                ps.setTimestamp(6, DateUtils.asTimestamp(user.getToDate()));
                ps.setBoolean(7, false);
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE t_user SET login=?, password=?, status=?, from_date=?, to_date=? WHERE id=?";
        update(sql, new Object[]{
                user.getLogin(),
                user.getPartyId(),
                user.getStatus().getCode(),
                DateUtils.asDate(user.getFromDate()),
                DateUtils.asDate(user.getToDate()),
                user.getId()
        });
    }

    @Override
    public void delete(long id) {
        String sql = "UPDATE t_user SET deleted=?, login=? WHERE id=?";
        update(sql, new Object[]{
                true,
                UUID.randomUUID().toString(),
                id
        });
    }

    private RowMapper<User> getRowMapper (){
        return new RowMapper() {
            @Override
            public User mapRow(final ResultSet rs, final int i) throws SQLException {
                final User obj = new User ();
                obj.setId(rs.getLong("id"));
                obj.setPartyId(rs.getLong("party_fk"));

                obj.setDeleted(rs.getBoolean("deleted"));

                obj.setLogin(rs.getString("login"));
                obj.setPassword(rs.getString("password"));
                obj.setStatus(UserStatus.fromText(rs.getString("status")));
                obj.setFromDate(DateUtils.asLocalDateTime(rs.getTimestamp("from_date")));
                obj.setToDate(DateUtils.asLocalDateTime(rs.getTimestamp("to_date")));
                return obj;
            }
        };
    }
}
