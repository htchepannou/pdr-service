package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.domain.Privacy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PartyElectronicAddressDaoImpl extends JdbcTemplate implements PartyElectronicAddressDao {
    public PartyElectronicAddressDaoImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<PartyElectronicAddress> findByParty(long partyId) {
        return query(
                "SELECT * FROM t_party_eaddress WHERE party_fk=?",
                new Object[] {partyId},
                getRowMapper()
        );
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
                domain.setPrivacy(Privacy.fromText(rs.getString("no_solicitation")));
                return domain;
            }
        };
    }
}
