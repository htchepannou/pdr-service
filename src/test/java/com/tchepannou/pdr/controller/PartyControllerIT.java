package com.tchepannou.pdr.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.tchepannou.pdr.Starter;
import com.tchepannou.pdr.dao.PartyElectronicAddressDao;
import com.tchepannou.pdr.domain.PartyElectronicAddress;
import com.tchepannou.pdr.domain.Privacy;
import com.tchepannou.pdr.dto.party.CreatePartyElectronicAddressResquest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Starter.class)
@WebIntegrationTest
@Sql({
        "/db/data/clean.sql",
        "/db/data/party.sql"
})
public class PartyControllerIT {
    @Value("${server.port}")
    int port;

    @Autowired
    private PartyElectronicAddressDao partyElectronicAddressDao;

    @Before
    public void setUp (){
        RestAssured.port = port;
    }

    @Test
    public void test_findById (){

        // @formatter:off
        when()
            .get("/api/parties/100")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(100))
            .body("name", is("Ray Sponsible"))
            .body("firstName", is("Ray"))
            .body("lastName", is("Sponsible"))
            .body("prefix", is("Mr"))
            .body("suffix", is("PHD"))
            .body("birthDate", is("1973-12-27 00:00:00"))
            .body("gender", is("MALE"))
            .body("kind", is("PERSON"))
        ;
        // @formatter:on
    }

    @Test
    public void test_findById_deleted(){
        // @formatter:off
        when()
            .get("/api/parties/200")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }

    @Test
    public void test_findById_badId (){
        // @formatter:off
        when()
            .get("/api/parties/99999")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }

    @Test
    public void test_contacts () {
        // @formatter:off
        when()
            .get("/api/parties/100/contacts")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("emailAddresses.id", hasItems(101, 102))
            .body("emailAddresses.noSolicitation", hasItems(false, true))
            .body("emailAddresses.privacy", hasItems("PUBLIC", "HIDDEN"))
            .body("emailAddresses.purpose", hasItems("primary", "secondary"))
            .body("emailAddresses.address", hasItems("ray.sponsible@gmail.com", "ray.sponsible@hotmail.com"))

            .body("webAddresses.id", hasItems(121, 122))
            .body("webAddresses.noSolicitation", hasItems(false, false))
            .body("webAddresses.privacy", hasItems("PUBLIC", "PUBLIC"))
            .body("webAddresses.purpose", hasItems("website", "facebook"))
            .body("webAddresses.address", hasItems("http://ray.sponsible.com", "https://facebook.com/ray_sponsible"))

            .body("postalAddresses.id", hasItems(131))
            .body("postalAddresses.noSolicitation", hasItems(false))
            .body("postalAddresses.privacy", hasItems("PUBLIC"))
            .body("postalAddresses.purpose", hasItems("postal"))
            .body("postalAddresses.street1", hasItems("3030 Linton"))
            .body("postalAddresses.city", hasItems("Montreal"))
            .body("postalAddresses.stateCode", hasItems("QC"))
            .body("postalAddresses.zipCode", hasItems("H1K 1H3"))
            .body("postalAddresses.countryCode", hasItems("CAN"))
        ;
        // @formatter:on
    }

    @Test
    public void test_contacts_badId (){
        // @formatter:off
        when()
            .get("/api/parties/99999/contacts")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }


    @Test
    public void test_addElectronicAddress () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("email", "primary", "john.smith@gmail.com");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .post("/api/parties/100/e-addresses")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .log()
                    .all()
                .body("noSolicitation", is(request.isNoSolicitation()))
                .body("purpose", is(request.getPurpose()))
                .body("address", is(request.getAddress()))
                .body("privacy", is(request.getPrivacy()))
                ;
        // @formatter:on
    }

    @Test
    public void test_addElectronicAddress_badPartyId () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("web", "website", "http://www.google.ca");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .post("/api/parties/9999/e-addresses/121")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .log()
                    .all()
                ;
        // @formatter:on
    }

    @Test
    public void test_updateElectronicAddress () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("web", "website", "http://www.google.ca");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .post("/api/parties/100/e-addresses/121")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .log()
                    .all()
                .body("noSolicitation", is(request.isNoSolicitation()))
                .body("purpose", is(request.getPurpose()))
                .body("address", is(request.getAddress()))
                .body("privacy", is(request.getPrivacy()))
                ;
        // @formatter:on
    }

    @Test
    public void test_updateElectronicAddress_badPartyId () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("web", "website", "http://www.google.ca");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .post("/api/parties/9999/e-addresses/121")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .log()
                    .all()
                ;
        // @formatter:on
    }
    @Test
    public void test_deleteElectronicAddress () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("web", "website", "http://www.google.ca");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .delete("/api/parties/100/e-addresses/121")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .log()
                    .all()
                ;
        // @formatter:on

        PartyElectronicAddress address = partyElectronicAddressDao.findById(121);
        assertThat(address).isNull();
    }

    @Test
    public void test_deleteElectronicAddress_badPartyId () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("web", "website", "http://www.google.ca");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .delete("/api/parties/9999/e-addresses/121")
        .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .log()
                    .all()
                ;
        // @formatter:on
    }

    //-- Attribute
    private CreatePartyElectronicAddressResquest buildCreatePartyElectronicAddressResquest (String type, String purpose, String value){
        CreatePartyElectronicAddressResquest address = new CreatePartyElectronicAddressResquest();
        address.setType(type);
        address.setPrivacy(Privacy.PUBLIC.name());
        address.setAddress(value);
        address.setNoSolicitation(true);
        address.setPurpose(purpose);
        return address;
    }
}
