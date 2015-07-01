package com.tchepannou.pdr.dto.party;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.ContactMechanismPurpose;
import com.tchepannou.pdr.domain.ElectronicAddress;
import com.tchepannou.pdr.domain.PartyElectronicAddress;

public class PartyElectronicAddressResponse extends AbstractPartyContactMechanismResponse{
    private String address;

    private PartyElectronicAddressResponse (final Builder builder){
        super(builder.partyElectronicAddress, builder.contactMechanismPurpose);

        this.address = builder.electronicAddress.getAddress();
    }

    public String getAddress() {
        return address;
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
