package com.tchepannou.pdr.controller;

import com.jayway.restassured.RestAssured;
import com.tchepannou.pdr.Starter;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.when;
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

    @Before
    public void setUp (){
        RestAssured.port = port;
    }

    @Test
    public void testFindById (){

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
    public void testFindById_badId (){
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
}
