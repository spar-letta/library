package com.javenock.book_service.repository;


import com.javenock.book_service.model.Kitabu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Kitabu, Long> {
    Optional<Kitabu> findByTitleIsIgnoreCaseAndDeletedIsFalse(String title);
}
