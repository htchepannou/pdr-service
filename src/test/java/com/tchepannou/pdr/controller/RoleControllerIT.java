package com.tchepannou.pdr.controller;

import com.jayway.restassured.RestAssured;
import com.tchepannou.pdr.Starter;
import com.tchepannou.pdr.dao.RoleDao;
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

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Starter.class)
@WebIntegrationTest
@Sql({
        "/db/data/clean.sql",
        "/db/data/role.sql"
})
public class RoleControllerIT {
    @Value("${server.port}")
    int port;

    @Autowired
    private RoleDao roleDao;


    @Before
    public void setUp (){
        RestAssured.port = port;
    }

    @Test
    public void test_findById (){
        // @formatter:off
        when()
            .get("/api/roles/1")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(1))
            .body("name", is("admin"))
        ;
        // @formatter:on
    }

    @Test
    public void test_findById_badId (){
        // @formatter:off
        when()
            .get("/api/roles/99999")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }

    @Test
    public void test_findAll (){
        // @formatter:off
        when()
            .get("/api/roles/")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("roles.id", contains(1, 2))
            .body("roles.name", contains("admin", "member"))
        ;
        // @formatter:on
    }

    @Test
    public void test_findPermissions(){
        // @formatter:off
        when()
            .get("/api/roles/1/permissions")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("permissions.id", contains(101, 102))
            .body("permissions.name", contains("Permission101", "Permission102"))
        ;
        // @formatter:on

    }
}
