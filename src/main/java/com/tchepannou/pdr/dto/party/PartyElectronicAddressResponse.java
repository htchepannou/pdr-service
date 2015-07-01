package com.tchepannou.pdr.dto.party;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.ContactMechanismPurpose;
import com.tchepannou.pdr.domain.ElectronicAddress;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.domain.Privacy;

public class PartyElectronicAddressResponse {
    private long id;
    private boolean noSolicitation;
    private String address;
    private String privacy;
    private String purpose;

    private PartyElectronicAddressResponse (final Builder builder){
        final PartyElectronicAddress partyElectronicAddress = builder.partyElectronicAddress;
        final Privacy aPrivacy = partyElectronicAddress.getPrivacy();
        this.id = partyElectronicAddress.getId();
        this.noSolicitation = partyElectronicAddress.isNoSolicitation();
        this.privacy = aPrivacy != null ? aPrivacy.toString() : null;

        this.purpose = builder.contactMechanismPurpose != null ? builder.contactMechanismPurpose.getName() : null;

        this.address = builder.electronicAddress.getAddress();
    }

    public long getId() {
        return id;
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

    public String getPurpose() {
        return purpose;
    }

    //-- Builder
    public static class Builder {
        private ElectronicAddress electronicAddress;
        private PartyElectronicAddress partyElectronicAddress;
        private ContactMechanismPurpose contactMechanismPurpose;

        public PartyElectronicAddressResponse build (){
            Preconditions.checkState(electronicAddress != null, "electronicAddress not set");
            Preconditions.checkState(partyElectronicAddress != null, "partyElectronicAddress not set");

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

        public Builder withContactMechanismPurpose(ContactMechanismPurpose purpose){
            this.contactMechanismPurpose = purpose;
            return this;
        }
    }
}
