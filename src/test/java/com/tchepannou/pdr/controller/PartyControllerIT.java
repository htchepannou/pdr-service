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
    public void test_addElectronicAddress () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("email", "primary", "john.smith@gmail.com");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .post("/api/parties/100/e-address")
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
    public void test_updateElectronicAddress () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("web", "website", "http://www.google.ca");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .post("/api/parties/100/e-address/121")
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
    public void test_deleteElectronicAddress () {
        final CreatePartyElectronicAddressResquest request = buildCreatePartyElectronicAddressResquest("web", "website", "http://www.google.ca");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
                .delete("/api/parties/100/e-address/121")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .log()
                    .all()
                ;
        // @formatter:on

        PartyElectronicAddress address = partyElectronicAddressDao.findById(121);
        assertThat(address).isNull();
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
