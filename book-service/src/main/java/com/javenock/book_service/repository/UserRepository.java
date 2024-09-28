package com.javenock.book_service.repository;

import com.javenock.book_service.model.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPublicIdAndDeletedIsFalse(UUID publicId);

    Optional<User> findByUserNameIgnoreCaseAndDeletedIsFalse(String trim);
}
