package com.tchepannou.pdr.dao.impl;

import com.tchepannou.pdr.domain.PartyContactMecanism;
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

public abstract class AbstractPartyContactMechanismDaoImpl<T extends PartyContactMecanism>
        extends JdbcTemplate
        implements com.tchepannou.pdr.dao.AbstractPartyContactMechanismDao<T>
{
    public AbstractPartyContactMechanismDaoImpl(final DataSource dataSource) {
        super(dataSource);
    }

    //-- Abstract
    protected abstract String getContactColumn ();
    
    protected abstract T createPartyContactMechanism();

    //-- Public
    @Override
    public T findById(final long id) {
        try {
            return queryForObject(
                    "SELECT * FROM t_party_contact_mechanism WHERE id=?",
                    new Object[]{id},
                    getRowMapper()
            );
        } catch (EmptyResultDataAccessException e) {    // NOSONAR
            return null;
        }
    }

    @Override
    public List<T> findByParty(final long partyId) {
        return query(
                "SELECT * FROM t_party_contact_mechanism WHERE party_fk=? AND " + getContactColumn() + " IS NOT NULL",
                new Object[] {partyId},
                getRowMapper()
        );
    }

    @Override
    public long create(final T address) {
        final KeyHolder holder = new GeneratedKeyHolder();
        update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final String sql = "INSERT INTO t_party_contact_mechanism(party_fk, " +
                        getContactColumn() +
                        ", type_fk, purpose_fk, no_solicitation, privacy) VALUES(?,?,?,?,?,?)";
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

    public void update(final T address) {
        final String sql = "UPDATE t_party_contact_mechanism SET " +
                getContactColumn()
                + "=?, purpose_fk=?, no_solicitation=?, privacy=?";
        update(sql,
                address.getContactId(),
                address.getPurposeId() == 0 ? null : address.getPurposeId(),
                address.isNoSolicitation(),
                String.valueOf(address.getPrivacy().getCode())
        );
    }

    public void delete(final long id) {
        update("DELETE FROM t_party_contact_mechanism WHERE id=?", id);
    }

    private RowMapper<T> getRowMapper (){
        return new RowMapper<T>() {
            @Override
            public T mapRow(final ResultSet rs, final int i) throws SQLException {
                final T obj = createPartyContactMechanism();
                obj.setId(rs.getLong("id"));
                obj.setPartyId(rs.getLong("party_fk"));
                obj.setContactId(rs.getLong(getContactColumn()));
                obj.setTypeId(rs.getLong("type_fk"));
                obj.setPurposeId(rs.getLong("purpose_fk"));
                obj.setNoSolicitation(rs.getBoolean("no_solicitation"));
                obj.setPrivacy(Privacy.fromText(rs.getString("privacy")));
                return obj;
            }
        };
    }
}
