package com.javenock.book_service.service;

import com.javenock.book_service.exception.MessageException;
import com.javenock.book_service.model.Kitabu;
import com.javenock.book_service.model.dataTypes.BookType;
import com.javenock.book_service.model.dto.BookRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface BookService {
    Kitabu createBook(BookRequestDto bookRequestDto) throws MessageException;

    Page<Kitabu> getBooks(String searchParam, UUID authorPublic, BookType bookType, LocalDate startDate, LocalDate endDate, Pageable pageable);

    void createBookFromCsv(Kitabu item);
}
