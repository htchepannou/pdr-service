package com.tchepannou.pdr.dto.party;

import com.google.common.base.Preconditions;
import com.tchepannou.pdr.domain.ContactMechanismPurpose;
import com.tchepannou.pdr.domain.PostalAddress;
import com.tchepannou.pdr.domain.PartyPostalAddress;

public class PartyPostalAddressResponse extends AbstractPartyContactMechanismResponse{
    private String street1;
    private String street2;
    private String city;
    private String stateCode;
    private String zip;
    private String countryCode;

    private PartyPostalAddressResponse(final Builder builder){
        super(builder.partyPostalAddress, builder.contactMechanismPurpose);

        this.street1 = builder.postalAddress.getStreet1();
        this.street2 = builder.postalAddress.getStreet2();
        this.city = builder.postalAddress.getCity();
        this.stateCode = builder.postalAddress.getStateCode();
        this.zip = builder.postalAddress.getZip();
        this.countryCode = builder.postalAddress.getCountryCode();
    }

    public String getStreet1() {
        return street1;
    }

    public String getStreet2() {
        return street2;
    }

    public String getCity() {
        return city;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getZip() {
        return zip;
    }

    public String getCountryCode() {
        return countryCode;
    }

    //-- Builder
    public static class Builder {
        private PostalAddress postalAddress;
        private PartyPostalAddress partyPostalAddress;
        private ContactMechanismPurpose contactMechanismPurpose;

        public PartyPostalAddressResponse build (){
            Preconditions.checkState(postalAddress != null, "postalAddress not set");
            Preconditions.checkState(partyPostalAddress != null, "partyPostalAddress not set");

            return new PartyPostalAddressResponse(this);
        }

        public Builder withPartyPostalAddress(PartyPostalAddress partyPostalAddress){
            this.partyPostalAddress = partyPostalAddress;
            return this;
        }

        public Builder withPostalAddress(PostalAddress postalAddress){
            this.postalAddress = postalAddress;
            return this;
        }

        public Builder withContactMechanismPurpose(ContactMechanismPurpose purpose){
            this.contactMechanismPurpose = purpose;
            return this;
        }
    }
}
