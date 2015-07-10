package com.tchepannou.pds.dao.impl;

import com.tchepannou.pds.dao.PhoneDao;
import com.tchepannou.pds.domain.Phone;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneDaoImpl extends AbstractContactMechanismDaoImpl<Phone> implements PhoneDao {
    public PhoneDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "t_phone";
    }

    @Override
    protected PreparedStatement preparedStatement(final Phone address, final Connection connection) throws SQLException {
        final String sql = "INSERT INTO t_phone(hash, country_code, number, extension) VALUES(?,?,?,?)";
        final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
        ps.setString(1, address.getHash());
        ps.setString(2, address.getCountryCode());
        ps.setString(3, address.getNumber());
        ps.setString(4, address.getExtension());
        return ps;
    }

    @Override
    protected RowMapper<Phone> getRowMapper (){
        return new RowMapper<Phone>() {
            @Override
            public Phone mapRow(final ResultSet rs, final int i) throws SQLException {
                final Phone obj = new Phone();
                obj.setId(rs.getLong("id"));
                obj.setHash(rs.getString("hash"));
                obj.setCountryCode(rs.getString("country_code"));
                obj.setNumber(rs.getString("number"));
                obj.setExtension(rs.getString("extension"));
                return obj;
            }
        };
    }
}
