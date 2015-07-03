package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyDao;
import com.tchepannou.pdr.enums.Gender;
import com.tchepannou.pdr.domain.Party;
import com.tchepannou.pdr.enums.PartyKind;
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

public class PartyDaoImpl extends JdbcTemplate implements PartyDao {
    public PartyDaoImpl (final DataSource ds) {
        super (ds);
    }

    @Override
    public Party findById (final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_party WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public Party findByUser (final long userId) {
        try {
            return queryForObject(
                    "SELECT P.* FROM t_party P JOIN t_user U ON P.id=U.party_fk WHERE U.id=? AND U.deleted=?",
                    new Object[]{userId, false},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public long create(Party party) {
        final KeyHolder holder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final String sql = "INSERT INTO t_party(name, first_name, last_name, prefix, suffix, birth_date, from_date, height, weight, gender, kind) "
                        + "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
                final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                final Gender gender = party.getGender();
                final PartyKind kind = party.getKind();

                ps.setString(1, party.getName());
                ps.setString(2, party.getFirstName());
                ps.setString(3, party.getLastName());
                ps.setString(4, party.getPrefix());
                ps.setString(5, party.getSuffix());
                ps.setDate(6, DateUtils.asSqlDate(party.getBirthDate()));
                ps.setTimestamp(7, DateUtils.asTimestamp(party.getFromDate()));
                ps.setInt(8, party.getHeigth());
                ps.setInt(9, party.getWeight());
                ps.setString(10, gender != null ? String.valueOf(gender.getCode()) : null);
                ps.setString(11, kind != null ? String.valueOf(kind.getCode()) : null);

                return ps;
            }
        }, holder);

        long id = holder.getKey().longValue();
        party.setId(id);
        return id;
    }

    @Override
    public void update(Party party) {
        final Gender gender = party.getGender();
        final PartyKind kind = party.getKind();
        final String sql = "UPDATE t_party " +
                " SET name=?, first_name=?, last_name=?, prefix=?, suffix=?, birth_date=?, height=?, weight=?, gender=?, kind=?" +
                " WHERE id=?";

        update (sql,
                party.getName(),
                party.getFirstName(),
                party.getLastName(),
                party.getPrefix(),
                party.getSuffix(),
                DateUtils.asSqlDate(party.getBirthDate()),
                party.getHeigth(),
                party.getWeight(),
                gender != null ? String.valueOf(gender.getCode()) : null,
                kind != null ? String.valueOf(kind.getCode()) : null,
                party.getId()
        );
    }

    private RowMapper<Party> getRowMapper (){
        return new RowMapper<Party>() {
            @Override
            public Party mapRow(final ResultSet rs, final int i) throws SQLException {
                final Party obj = new Party ();
                obj.setId(rs.getLong("id"));

                obj.setFromDate(rs.getTimestamp("from_date"));

                obj.setName(rs.getString("name"));
                obj.setFirstName(rs.getString("first_name"));
                obj.setLastName(rs.getString("last_name"));
                obj.setPrefix(rs.getString("prefix"));
                obj.setSuffix(rs.getString("suffix"));
                obj.setBirthDate(rs.getDate("birth_date"));
                obj.setHeigth(rs.getInt("height"));
                obj.setWeight(rs.getInt("weight"));
                obj.setGender(Gender.fromText(rs.getString("gender")));
                obj.setKind(PartyKind.fromText(rs.getString("kind")));
                return obj;
            }
        };
    }
}
