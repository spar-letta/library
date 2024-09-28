package com.javenock.book_service.service;

import com.javenock.book_service.exception.MessageException;
import com.javenock.book_service.model.Kitabu;
import com.javenock.book_service.model.dataTypes.BookType;
import com.javenock.book_service.model.dto.BookRequestDto;
import com.javenock.book_service.model.vo.User;
import com.javenock.book_service.repository.BookRepository;
import com.javenock.book_service.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Override
    public Kitabu createBook(BookRequestDto bookRequestDto) throws MessageException {
        if (ObjectUtils.isEmpty(bookRequestDto.getAuthorIds())) {
            throw new MessageException("Author IDs cannot be empty");
        }

        if (bookRequestDto.getDatePublished() == null) {
            throw new MessageException("Sorry Published Date cannot be null");
        }
        Kitabu book = new Kitabu();
        book.setTitle(bookRequestDto.getTitle());
        book.setType(bookRequestDto.getType());
        book.setDescription(bookRequestDto.getDescription());
        book.setPrice(bookRequestDto.getPrice());
        book.setDatePublished(bookRequestDto.getDatePublished());
        Set<User> authorList = bookRequestDto.getAuthorIds().stream().map(publicId -> validateAuthor(publicId)).collect(Collectors.toSet());
        if (!authorList.isEmpty()) {
            book.setAuthor(authorList);
        }

        return bookRepository.save(book);
    }

    @Override
    public Page<Kitabu> getBooks(String searchParam, UUID authorPublic, BookType bookType, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Kitabu> cq = cb.createQuery(Kitabu.class);
        Root<Kitabu> root = cq.from(Kitabu.class);

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Kitabu> countRoot = countQuery.from(Kitabu.class);

        cq.distinct(true);

        final List<Predicate> andPredicates = new ArrayList<>();

        Predicate deletedPredicate = cb.equal(root.get("deleted"), Boolean.FALSE);
        andPredicates.add(deletedPredicate);

        if (bookType != null) {
            Predicate newPredicate = cb.equal(root.get("type"), bookType);
            andPredicates.add(newPredicate);
        }

        if (startDate != null && endDate != null) {
            Predicate newPredicate = cb.between(root.get("datePublished"), startDate, endDate);
            andPredicates.add(newPredicate);
        }

        if(authorPublic != null){
            Join<Kitabu, User> bookAuthor = root.join("author", JoinType.INNER);
            countRoot.join("author", JoinType.INNER);

            Predicate newPredicate = cb.equal(bookAuthor.get("publicId"), authorPublic);
            andPredicates.add(newPredicate);
        }

        if (searchParam != null && searchParam.trim().length() >= 3) {
            final List<Predicate> orPredicates = new ArrayList<>();
            orPredicates.add(cb.like(cb.upper(root.get("title")), "%" + searchParam.toUpperCase() + "%"));
            Predicate p = cb.or(orPredicates.toArray(new Predicate[orPredicates.size()]));
            andPredicates.add(p);
        }

        cq.where(andPredicates.toArray(new Predicate[andPredicates.size()])).orderBy(cb.desc(root.get("id")));

        TypedQuery<Kitabu> query = entityManager.createQuery(cq).setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        List<Kitabu> queryResultList = query.getResultList();

        return new PageImpl<>(queryResultList, pageable, queryResultList.size());
    }

    @Override
    public void createBookFromCsv(Kitabu kitabu) {
       Optional<Kitabu> optionalKitabu = bookRepository.findByTitleIsIgnoreCaseAndDeletedIsFalse(kitabu.getTitle());
       if (!optionalKitabu.isPresent()) {
           Set<User> authors = new HashSet<>();
           if (!kitabu.getAuthorList().isEmpty()) {
               kitabu.getAuthorList().forEach(item -> {
                   String[] authorNames = item.split(",");
                   Arrays.stream(authorNames).forEach(authorName -> {
                       Optional<User> optionalAuthor = userRepository.findByUserNameIgnoreCaseAndDeletedIsFalse(authorName.trim());
                       if (optionalAuthor.isPresent()) {
                           authors.add(optionalAuthor.get());
                       }
                   });
               });
           }
           Kitabu book = new Kitabu();
           book.setTitle(kitabu.getTitle());
           book.setType(kitabu.getType());
           book.setDescription(kitabu.getDescription());
           book.setPrice(kitabu.getPrice());
           book.setDatePublished(kitabu.getDatePublished());
           if(!authors.isEmpty()) book.setAuthor(authors);
           Kitabu saved = bookRepository.save(book);
           log.info("===================Saved book: {}", saved);
       }
    }

    private User validateAuthor(UUID publicId) {
        Optional<User> optionalUser = userRepository.findByPublicIdAndDeletedIsFalse(publicId);
        if (!optionalUser.isPresent()) {
            new MessageException("user not found");
        }
        return optionalUser.get();
    }
}
