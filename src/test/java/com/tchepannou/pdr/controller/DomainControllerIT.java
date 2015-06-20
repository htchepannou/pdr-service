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
import static org.hamcrest.Matchers.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Starter.class)
@WebIntegrationTest
@Sql({
        "/db/data/clean.sql",
        "/db/data/domain.sql"
})
public class DomainControllerIT {
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
            .get("/api/domains/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(1))
            .body("name", is("admin.moralab.com"))
            .body("description", is("Admin site"))
        ;
        // @formatter:on
    }
    @Test
    public void testFindById_badId (){
        // @formatter:off
        when()
            .get("/api/domains/99999")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }


    @Test
    public void testFindAll (){
        // @formatter:off
        when()
            .get("/api/domains/")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", contains(1, 2, 3))
            .body("name", contains("admin.moralab.com", "www.moralab.com", "api.moralab.com"))
            .body("description", contains("Admin site", "Main site", "API site"))
        ;
        // @formatter:on
    }
}
