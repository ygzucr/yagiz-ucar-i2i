package com.example.spring_boot_bootstrap;

import com.example.spring_boot_bootstrap.model.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootBootstrapLiveTest {

    @LocalServerPort
    private int port;
    private String API_ROOT;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        API_ROOT = "http://localhost:" + port + "/api/books";
    }

    private Book createRandomBook() {
        Book b = new Book();
        b.setTitle(RandomStringUtils.randomAlphabetic(10));
        b.setAuthor(RandomStringUtils.randomAlphabetic(15));
        return b;
    }

    private String createBookAsUri(Book book) {
        Response res = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);
        assertEquals(201, res.getStatusCode());
        return API_ROOT + "/" + res.jsonPath().getLong("id");
    }

    @Test
    void whenGetAllBooks_thenOK() {
        Response res = RestAssured.get(API_ROOT);
        assertEquals(200, res.getStatusCode());
    }

    @Test
    void whenCreateNewBook_thenCreated() {
        Book book = createRandomBook();
        Response res = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);

        assertEquals(201, res.getStatusCode());
        assertNotNull(res.jsonPath().get("id"));
    }

    @Test
    void whenGetBooksByTitle_thenOK() {
        Book book = createRandomBook();
        createBookAsUri(book);

        Response res = RestAssured.get(API_ROOT + "/title/" + book.getTitle());
        assertEquals(200, res.getStatusCode());
        List<?> list = res.jsonPath().getList("");
        assertFalse(list.isEmpty());
    }

    @Test
    void whenGetCreatedBookById_thenOK() {
        Book book = createRandomBook();
        String uri = createBookAsUri(book);

        Response res = RestAssured.get(uri);
        assertEquals(200, res.getStatusCode());
        assertEquals(book.getTitle(), res.jsonPath().getString("title"));
    }




    @Test
    void whenInvalidBook_thenError() {
        Book book = createRandomBook();
        book.setAuthor(null);

        Response res = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .post(API_ROOT);

        assertEquals(400, res.getStatusCode());
    }

    @Test
    void whenUpdateCreatedBook_thenUpdated() {
        Book book = createRandomBook();
        String uri = createBookAsUri(book);
        book.setId(Long.parseLong(uri.substring(uri.lastIndexOf('/') + 1)));
        book.setAuthor("newAuthor");

        Response res = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(book)
                .put(uri);
        assertEquals(200, res.getStatusCode());

        Response get = RestAssured.get(uri);
        assertEquals("newAuthor", get.jsonPath().getString("author"));
    }
}