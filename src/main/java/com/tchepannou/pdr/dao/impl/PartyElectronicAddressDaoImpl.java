package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.domain.Privacy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class PartyElectronicAddressDaoImpl extends JdbcTemplate implements PartyElectronicAddressDao {
    public PartyElectronicAddressDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public PartyElectronicAddress findById(final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_party_eaddress WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<PartyElectronicAddress> findByParty(final long partyId) {
        return query(
                "SELECT * FROM t_party_eaddress WHERE party_fk=?",
                new Object[] {partyId},
                getRowMapper()
        );
    }

    @Override
    public long create(final PartyElectronicAddress address) {
        final KeyHolder holder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final String sql = "INSERT INTO t_party_eaddress(party_fk, contact_fk, type_fk, purpose_fk, no_solicitation, privacy) VALUES(?,?,?,?,?,?)";
                final PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
                final Privacy privacy = address.getPrivacy();

                ps.setLong(1, address.getPartyId());
                ps.setLong(2, address.getContactId());
                ps.setLong(3, address.getTypeId());

                if (address.getPurposeId() == 0){
                    ps.setNull(4, Types.BIGINT);
                } else {
                    ps.setLong(4, address.getPurposeId());
                }
                ps.setBoolean(5, address.isNoSolicitation());
                ps.setString(6, privacy != null ? String.valueOf(privacy.getCode()) : null);
                return ps;
            }
        }, holder);

        return holder.getKey().longValue();
    }

    @Override
    public void update(final PartyElectronicAddress address) {
        final String sql = "UPDATE t_party_eaddress SET contact_fk=?, purpose_fk=?, no_solicitation=?, privacy=?";
        update(sql,
                address.getContactId(),
                address.getPurposeId() == 0 ? null : address.getPurposeId(),
                address.isNoSolicitation(),
                String.valueOf(address.getPrivacy().getCode())
        );
    }

    @Override
    public void delete(final long id) {
        update("DELETE FROM t_party_eaddress WHERE id=?", id);
    }

    private RowMapper<PartyElectronicAddress> getRowMapper (){
        return new RowMapper<PartyElectronicAddress>() {
            @Override
            public PartyElectronicAddress mapRow(final ResultSet rs, final int i) throws SQLException {
                final PartyElectronicAddress domain = new PartyElectronicAddress();
                domain.setId(rs.getLong("id"));
                domain.setPartyId(rs.getLong("party_fk"));
                domain.setContactId(rs.getLong("contact_fk"));
                domain.setTypeId(rs.getLong("type_fk"));
                domain.setPurposeId(rs.getLong("purpose_fk"));
                domain.setNoSolicitation(rs.getBoolean("no_solicitation"));
                domain.setPrivacy(Privacy.fromText(rs.getString("privacy")));
                return domain;
            }
        };
    }
}
