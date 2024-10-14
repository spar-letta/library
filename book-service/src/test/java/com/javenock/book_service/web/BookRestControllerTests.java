package com.javenock.book_service.web;

import com.javenock.book_service.BookServiceApplicationTests;
import com.javenock.book_service.model.dataTypes.BookType;
import com.javenock.book_service.model.dto.BookRequestDto;
import com.javenock.book_service.service.BatchJobService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookRestControllerTests extends BookServiceApplicationTests {

    @Autowired
    private BatchJobService batchJobService;

    @Test
    public void createBook() {
        BookRequestDto bookRequestDto = new BookRequestDto();
        bookRequestDto.setTitle("Title");
        bookRequestDto.setDescription("Description");
        bookRequestDto.setType(BookType.Trade_Books);
        bookRequestDto.setPrice(new BigDecimal("100.00"));
        bookRequestDto.setDatePublished(LocalDate.now().minusYears(3));
        bookRequestDto.setAuthorIds(Collections.singleton(UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9")));

        given()
                .auth()
                .oauth2(accessToken)
                .contentType("application/json")
                .body(bookRequestDto)
                .post("/books")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void getBook() {
        given()
                .auth()
                .oauth2(accessToken)
                .queryParam("searchParam", "New Covent")
                .queryParam("type", BookType.Trade_Books)
                .queryParam("author", UUID.fromString("7a40fd66-36a6-4487-9509-e9b12a61bff9"))
                .queryParam("startDate", LocalDate.now().minusYears(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .queryParam("endDate",LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .get("/books")
                .then().log().all()
                .statusCode(200)
                .body("content.size()", greaterThanOrEqualTo(1))
                .body("content[0].type", equalToIgnoringCase("Trade_Books"))
                .body("content[0].description", equalToIgnoringCase("description"))
                .body("content[0].price", equalTo(120.00f))
                .body("content[0].author", notNullValue());

    }

    @Test
    public void getAllBooksCsvImport() {
        batchJobService.processKitabuImport();
        given()
                .auth()
                .oauth2(accessToken)
                .get("/books")
                .then().log().all()
                .statusCode(200)
                .body("content.size()", greaterThanOrEqualTo(1));
    }
}
