package com.tchepannou.pds.dto.party;

import java.util.ArrayList;
import java.util.List;

public class ContactMechanismListResponse {
    //-- Attributes
    private List<PartyElectronicAddressResponse> emailAddresses;
    private List<PartyElectronicAddressResponse> webAddresses;
    private List<PartyPostalAddressResponse> postalAddresses;
    private List<PartyPhoneResponse> phones;


    //-- Constructor
    public ContactMechanismListResponse(final Builder builder){
        this.emailAddresses = builder.emailAddresses;
        this.webAddresses = builder.webAddresses;
        this.postalAddresses = builder.postalAddresses;
        this.phones = builder.phones;
    }
    
    //-- Builder
    public static class Builder {
        private List<PartyElectronicAddressResponse> emailAddresses;
        private List<PartyElectronicAddressResponse> webAddresses;
        private List<PartyPostalAddressResponse> postalAddresses;
        private List<PartyPhoneResponse> phones;

        public ContactMechanismListResponse build () {
            return new ContactMechanismListResponse(this);
        }

        public Builder addEmailAddress (final PartyElectronicAddressResponse address) {
            if (emailAddresses == null){
                emailAddresses = new ArrayList<>();
            }
            emailAddresses.add(address);
            return this;
        }

        public Builder addWebAddress (final PartyElectronicAddressResponse address) {
            if (webAddresses == null){
                webAddresses = new ArrayList<>();
            }
            webAddresses.add(address);
            return this;
        }

        public Builder addPostalAddress (final PartyPostalAddressResponse address) {
            if (postalAddresses == null){
                postalAddresses = new ArrayList<>();
            }
            postalAddresses.add(address);
            return this;
        }

        public Builder addPhone (final PartyPhoneResponse phone) {
            if (phones == null){
                phones = new ArrayList<>();
            }
            phones.add(phone);
            return this;
        }
    }


    //-- Getter
    public List<PartyElectronicAddressResponse> getEmailAddresses() {
        return emailAddresses;
    }

    public List<PartyElectronicAddressResponse> getWebAddresses() {
        return webAddresses;
    }

    public List<PartyPostalAddressResponse> getPostalAddresses() {
        return postalAddresses;
    }

    public List<PartyPhoneResponse> getPhones() {
        return phones;
    }

}
