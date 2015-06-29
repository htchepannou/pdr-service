package com.tchepannou.pdr.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.tchepannou.pdr.Starter;
import com.tchepannou.pdr.dao.DomainDao;
import com.tchepannou.pdr.dto.domain.DomainRequest;
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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.greaterThan;

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

    @Autowired
    private DomainDao domainDao;


    @Before
    public void setUp (){
        RestAssured.port = port;
    }

    @Test
    public void test_findById (){
        // @formatter:off
        when()
            .get("/api/domains/100")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(100))
            .body("name", is("admin.moralab.com"))
            .body("description", is("Admin site"))
        ;
        // @formatter:on
    }

    @Test
    public void test_findById_badId (){
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
    public void test_findById_deleted (){
        // @formatter:off
        when()
            .get("/api/domains/103")
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
            .get("/api/domains/")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", contains(100, 101, 102, 200, 300))
            .body("name", contains("admin.moralab.com", "www.moralab.com", "api.moralab.com", "update.me", "delete.me"))
            .body("description", contains("Admin site", "Main site", "API site", null, null))
        ;
        // @formatter:on
    }

    @Test
    public void test_create () throws Exception {
        final DomainRequest req = createDomainRequest("create", "this is a test");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(req, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/domains/")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", greaterThan(0))
            .body("name", is(req.getName()))
            .body("description", is(req.getDescription()))
        ;
        // @formatter:on
    }

    @Test
    public void test_create_duplicateName () {
        final DomainRequest req = createDomainRequest("api.moralab.com", "this is a test");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(req)
        .when()
            .post("/api/domains/")
        .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .log()
                .all()
        ;
        // @formatter:on
    }

    @Test
    public void test_update () {
        final DomainRequest req = createDomainRequest("update", "this is a test");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(req, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/domains/200")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(200))
            .body("name", is(req.getName()))
            .body("description", is(req.getDescription()))
        ;
        // @formatter:on
    }

    @Test
    public void test_update_duplicateName () {
        final DomainRequest req = createDomainRequest("api.moralab.com", "this is a test");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(req)
        .when()
            .post("/api/domains/200")
        .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .log()
                .all()
        ;
        // @formatter:on
    }


    @Test
    public void test_update_not_found () {
        final DomainRequest req = createDomainRequest("update_not_found", "this is a test");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(req, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/domains/99999")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all();
    }

    @Test
    public void test_update_deleted () {
        final DomainRequest req = createDomainRequest("update_deleted", "this is a test");

        // @formatter:off
        given()
                .contentType(ContentType.JSON)
                .content(req, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/domains/201")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all();
    }

    @Test
    public void test_delete () {
        // @formatter:off
        when()
            .delete("/api/domains/300")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all();
        // @formatter:on

        assertThat(domainDao.findById(300)).isNull();
    }

    private DomainRequest createDomainRequest (String name, String description){
        final DomainRequest req = new DomainRequest();
        req.setName(name);
        req.setDescription(description);

        return req;
    }
}
