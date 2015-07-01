package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PostalAddressDao;
import com.tchepannou.pdr.domain.PostalAddress;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostalAddressDaoImpl extends AbstractContactMechanismDaoImpl<PostalAddress> implements PostalAddressDao {
    public PostalAddressDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "t_paddress";
    }

    @Override
    protected PreparedStatement preparedStatement(final PostalAddress address, final Connection connection) throws SQLException {
        final String sql = "INSERT INTO t_paddress(hash, street1, street2, city, state_code, zip_code, country_code) VALUES(?,?,?,?,?,?,?)";
        final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
        ps.setString(1, address.getHash());
        ps.setString(2, address.getStreet1());
        ps.setString(3, address.getStreet2());
        ps.setString(4, address.getCity());
        ps.setString(5, address.getStateCode());
        ps.setString(6, address.getZipCode());
        ps.setString(7, address.getCountryCode());
        return ps;
    }

    @Override
    protected RowMapper<PostalAddress> getRowMapper (){
        return new RowMapper<PostalAddress>() {
            @Override
            public PostalAddress mapRow(final ResultSet rs, final int i) throws SQLException {
                final PostalAddress obj = new PostalAddress();
                obj.setId(rs.getLong("id"));
                obj.setHash(rs.getString("hash"));
                obj.setStreet1(rs.getString("street1"));
                obj.setStreet2(rs.getString("street2"));
                obj.setCity(rs.getString("city"));
                obj.setStateCode(rs.getString("state_code"));
                obj.setZipCode(rs.getString("zip_code"));
                obj.setCountryCode(rs.getString("country_code"));
                return obj;
            }
        };
    }
}
