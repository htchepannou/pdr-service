package com.tchepannou.pdr.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.tchepannou.pdr.Starter;
import com.tchepannou.pdr.dto.user.CreateUserRequest;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.number.OrderingComparison.greaterThan;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Starter.class)
@WebIntegrationTest
@Sql({
        "/db/data/clean.sql",
        "/db/data/user.sql"
})
public class UserControllerIT {
    @Value("${server.port}")
    int port;

    @Before
    public void setUp (){
        RestAssured.port = port;
    }

    @Test
    public void test_findById (){

        // @formatter:off
        when()
            .get("/api/users/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(1))
            .body("partyId", is(1))
            .body("login", is("ray.sponsible"))
            .body("password", nullValue())
            .body("status", is("ACTIVE"))
            .body("fromDate", is("1973-12-27T10:30:45"))
            .body("toDate", nullValue())
        ;
        // @formatter:on
    }

    @Test
    public void test_findById_badId (){
        // @formatter:off
        when()
            .get("/api/users/99999")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }

    @Test
    public void test_create () throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setPartyId(1);
        request.setLogin("john.smith");
        request.setPassword("__secret__");
        String json = new ObjectMapper().writeValueAsString(request);

        // @formatter:off
        given ()
                .contentType(ContentType.JSON)
                .body(json)
        .when()
            .put("/api/users")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(1))
            .body("partyId", is(1))
            .body("login", is("john.smith"))
            .body("password", nullValue())
            .body("status", is("CREATED"))
            .body("fromDate", greaterThan("1973-12-27T10:30:45"))
            .body("toDate", nullValue())
        ;
        // @formatter:on

    }
}
