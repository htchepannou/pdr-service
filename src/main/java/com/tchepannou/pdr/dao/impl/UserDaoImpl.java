package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.domain.*;
import com.tchepannou.pdr.util.DateUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl extends JdbcTemplate implements UserDao {
    public UserDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public User findById(long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_user WHERE id=?",
                    new Object[]{id},
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
                    "SELECT * FROM t_user WHERE party_fk=?",
                    new Object[]{partyId},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public void create(User user) {
        String sql = "INSERT INTO t_user(party_fk, login, password, status, from_date, to_date) VALUES(?,?,?,?,?,?)";
        update(sql, new Object[]{
             user.getPartyId(),
                user.getLogin(),
                user.getPartyId(),
                user.getStatus().getCode(),
                DateUtils.asDate(user.getFromDate()),
                DateUtils.asDate(user.getToDate())
        });
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



    private RowMapper<User> getRowMapper (){
        return new RowMapper() {
            @Override
            public User mapRow(final ResultSet rs, final int i) throws SQLException {
                final User obj = new User ();
                obj.setId(rs.getLong("id"));
                obj.setLogin(rs.getString("login"));
                obj.setPassword(rs.getString("password"));
                obj.setStatus(UserStatus.fromText(rs.getString("status")));
                obj.setFromDate(DateUtils.asLocalDateTime(rs.getTimestamp("from_date")));
                obj.setToDate(DateUtils.asLocalDateTime(rs.getTimestamp("to_date")));
                obj.setPartyId(rs.getLong("party_fk"));
                return obj;
            }
        };
    }
}
