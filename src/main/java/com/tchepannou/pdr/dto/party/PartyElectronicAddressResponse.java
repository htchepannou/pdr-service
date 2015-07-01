package com.tchepannou.pdr.dto.party;

import com.tchepannou.pdr.domain.ElectronicAddress;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.domain.Privacy;

public class PartyElectronicAddressResponse {
    private boolean noSolicitation;
    private String address;
    private String privacy;

    private PartyElectronicAddressResponse (final Builder builder){
        final PartyElectronicAddress partyElectronicAddress = builder.partyElectronicAddress;
        final Privacy privacy = partyElectronicAddress.getPrivacy();
        this.noSolicitation = partyElectronicAddress.isNoSolicitation();
        this.privacy = privacy != null ? privacy.toString() : null;

        this.address = builder.electronicAddress.getAddress();
    }

    public boolean isNoSolicitation() {
        return noSolicitation;
    }

    public String getAddress() {
        return address;
    }

    public String getPrivacy() {
        return privacy;
    }

    //-- Builder
    public static class Builder {
        private ElectronicAddress electronicAddress;
        private PartyElectronicAddress partyElectronicAddress;

        public PartyElectronicAddressResponse build (){
            return new PartyElectronicAddressResponse(this);
        }

        public Builder withPartyElectronicAddress(PartyElectronicAddress partyElectronicAddress){
            this.partyElectronicAddress = partyElectronicAddress;
            return this;
        }

        public Builder withElectronicAddress(ElectronicAddress electronicAddress){
            this.electronicAddress = electronicAddress;
            return this;
        }
    }
}
