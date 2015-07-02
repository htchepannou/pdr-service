package com.tchepannou.pdr.controller;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.mapper.ObjectMapperType;
import com.tchepannou.pdr.Starter;
import com.tchepannou.pdr.dao.UserDao;
import com.tchepannou.pdr.domain.User;
import com.tchepannou.pdr.enums.UserStatus;
import com.tchepannou.pdr.dto.user.CreateUserRequest;
import com.tchepannou.pdr.dto.user.UpdateUserRequest;
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
import static org.hamcrest.CoreMatchers.*;
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

    @Autowired
    private UserDao userDao;

    @Before
    public void setUp (){
        RestAssured.port = port;
    }

    @Test
    public void test_findById (){

        // @formatter:off
        when()
            .get("/api/users/101")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(101))
            .body("partyId", is(100))
            .body("login", is("ray.sponsible"))
            .body("password", nullValue())
            .body("status", is("ACTIVE"))
            .body("fromDate", is("1973-12-27 10:30:45"))
            .body("toDate", nullValue())
        ;
        // @formatter:on
    }

    @Test
    public void test_findById_deletedParty (){

        // @formatter:off
        when()
            .get("/api/users/201")
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
            .get("/api/users/301")
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
        request.setPartyId(100);
        request.setLogin("john.smith");
        request.setPassword("__secret__");

        // @formatter:off
        given ()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/users")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", greaterThan(1))
            .body("partyId", is(100))
            .body("login", is("john.smith"))
            .body("password", nullValue())
            .body("status", is("CREATED"))
            .body("fromDate", notNullValue())
            .body("toDate", nullValue())
        ;
        // @formatter:on
    }

    @Test
    public void test_create_duplicateLogin () throws Exception {
        CreateUserRequest request = new CreateUserRequest();
        request.setPartyId(100);
        request.setLogin("ray.sponsible");
        request.setPassword("__secret__");

        // @formatter:off
        given ()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/users")
        .then()
            .statusCode(HttpStatus.SC_CONFLICT)
            .log()
                .all()
            .body("message", is("duplicate_login"))
        ;
        // @formatter:on
    }

    @Test
    public void test_update () throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setLogin("john.smith");
        request.setPassword("__secret__");
        request.setStatus(UserStatus.ACTIVE.name());

        // @formatter:off
        given ()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/users/1001")
        .then()
            .statusCode(HttpStatus.SC_OK)
            .log()
                .all()
            .body("id", is(1001))
            .body("partyId", is(1000))
            .body("login", is("john.smith"))
            .body("password", nullValue())
            .body("status", is("ACTIVE"))
            .body("fromDate", is("1973-12-27 10:30:45"))
            .body("toDate", nullValue())
        ;
        // @formatter:on
    }

    @Test
    public void test_update_deleted () throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setLogin("john.smith");
        request.setPassword("__secret__");
        request.setStatus(UserStatus.ACTIVE.name());

        // @formatter:off
        given ()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/users/1101")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }

    @Test
    public void test_update_badId () throws Exception {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setLogin("john.smith");
        request.setPassword("__secret__");
        request.setStatus(UserStatus.ACTIVE.name());

        // @formatter:off
        given ()
                .contentType(ContentType.JSON)
                .content(request, ObjectMapperType.JACKSON_2)
        .when()
            .post("/api/users/9999999")
        .then()
            .statusCode(HttpStatus.SC_NOT_FOUND)
            .log()
                .all()
        ;
        // @formatter:on
    }

    @Test
    public void test_delete () throws Exception {
        // @formatter:off
        when()
            .delete("/api/users/2001")
        .then()
                .statusCode(HttpStatus.SC_OK)
                .log()
            .all()
        ;
        // @formatter:on

        User user = userDao.findById(2001);
        assertThat(user.isDeleted()).isTrue();
        assertThat(user.getFromDate()).isNotNull();
        assertThat(user.getToDate()).isNotNull();
        assertThat(user.getLogin()).matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    @Test
    public void test_delete_badId () throws Exception {
        // @formatter:off
        when()
            .delete("/api/users/9999")
        .then()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .log()
            .all()
        ;
        // @formatter:on
    }
}
