package com.javenock.book_service.web;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.book_service.docs.Examples;
import com.javenock.book_service.exception.MessageException;
import com.javenock.book_service.model.Kitabu;
import com.javenock.book_service.model.dataTypes.BookType;
import com.javenock.book_service.model.dto.BookRequestDto;
import com.javenock.book_service.service.BookService;
import com.javenock.book_service.views.BaseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @PreAuthorize("hasAuthority('CREATE_BOOK')")
    @PostMapping
    @JsonView({BaseView.BookView.class})
    @Operation(summary = "Create Book", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.GET_BOOK_OK_RESPONSE)))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {@ExampleObject(name = "Book creation request", value = Examples.CREATE_BOOK_REQUEST)})))
    public Kitabu createBook(@RequestBody @Valid BookRequestDto bookRequestDto) throws MessageException {
        return bookService.createBook(bookRequestDto);
    }

    @PreAuthorize("hasAuthority('READ_BOOKS')")
    @GetMapping
    @Operation(summary = "Create Book", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.GET_BOOKS_OK_RESPONSE)))})
    @JsonView({BaseView.BookView.class})
    @PageableAsQueryParam
    public Page<Kitabu> getBooks(
            @RequestParam(value = "searchParam", required = false) String searchParam, // Book Title
            @RequestParam(value = "author", required = false) UUID authorPublic,
            @RequestParam(value = "type", required = false) BookType bookType,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @Parameter(example = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") @Parameter(example = "yyyy-MM-dd") LocalDate endDate,
            @Parameter(hidden = true) Pageable pageable) {
        return bookService.getBooks(searchParam, authorPublic, bookType, startDate, endDate, pageable);
    }
}
