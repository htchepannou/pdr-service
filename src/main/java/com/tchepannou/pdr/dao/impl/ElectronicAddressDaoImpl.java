package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.ElectronicAddressDao;
import com.tchepannou.pdr.domain.ElectronicAddress;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ElectronicAddressDaoImpl extends AbstractContactMechanismDaoImpl<ElectronicAddress> implements ElectronicAddressDao {
    public ElectronicAddressDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getTableName() {
        return "t_eaddress";
    }

    @Override
    protected PreparedStatement preparedStatement(final ElectronicAddress address, final Connection connection) throws SQLException {
        final String sql = "INSERT INTO t_eaddress(address, hash) VALUES(?,?)";
        final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
        ps.setString(1, address.getAddress());
        ps.setString(2, address.getHash());
        return ps;
    }

    @Override
    protected RowMapper<ElectronicAddress> getRowMapper (){
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
