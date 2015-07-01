package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.ElectronicAddressDao;
import com.tchepannou.pdr.domain.ElectronicAddress;
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ElectronicAddressDaoImpl extends JdbcTemplate implements ElectronicAddressDao {
    public ElectronicAddressDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public ElectronicAddress findByAddress(final String address) {
        try {
            return queryForObject(
                    "SELECT * FROM t_eaddress WHERE hash=?",
                    new Object[]{ ElectronicAddress.hash(address)},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<ElectronicAddress> findByIds(Collection<? extends Long> ids) {
        if (ids.isEmpty()){
            return Collections.emptyList();
        }

        final StringBuilder sql = new StringBuilder("SELECT * FROM t_eaddress WHERE id IN (");
        for (int i=0, size=ids.size() ; i<size ; i++) {
            if (i>0){
                sql.append(',');
            }
            sql.append('?');
        }
        sql.append(")");

        return query(sql.toString(), ids.toArray(), getRowMapper());
    }

    @Override
    public long create(final ElectronicAddress address) {
        final KeyHolder holder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final String sql = "INSERT INTO t_eaddress(hash, address) VALUES(?,?)";
                final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                ps.setString(2, address.getAddress());
                ps.setString(3, address.getHash());
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    private RowMapper<ElectronicAddress> getRowMapper (){
        return new RowMapper<ElectronicAddress>() {
            @Override
            public ElectronicAddress mapRow(final ResultSet rs, final int i) throws SQLException {
                final ElectronicAddress obj = new ElectronicAddress();
                obj.setId(rs.getLong("id"));
                obj.setAddress(rs.getString("address"));
                obj.setHash(rs.getString("hash"));
                return obj;
            }
        };
    }
}
