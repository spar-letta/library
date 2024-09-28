package com.javenock.book_service.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.javenock.book_service.model.dataTypes.BookType;
import com.javenock.book_service.model.vo.User;
import com.javenock.book_service.views.BaseView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(schema = "library", name = "books")
@Where(clause = "deleted = false")
public class Kitabu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    @Column(name = "title")
    @JsonView({BaseView.BookView.class})
    private String title;

    @Column(name = "description")
    @JsonView({BaseView.BookView.class})
    private String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    @JsonView({BaseView.BookView.class})
    private BookType type;

    @Column(name = "date_published")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonView({BaseView.BookView.class})
    private LocalDate datePublished;

    @Column(name = "price")
    @JsonView({BaseView.BookView.class})
    private BigDecimal price = new BigDecimal("0.00");

    @ManyToMany
    @JoinTable(name = "book_author", schema = "library",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @JsonView({BaseView.BookView.class})
    private Set<User> author = new HashSet<>();

    @Transient
    private Set<String> authorList = Collections.emptySet();

    @Transient
    private String datePublishedString;

    public Kitabu(String title, String description, BookType type, LocalDate datePublished, BigDecimal price, Set<String> authorList) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.datePublished = datePublished;
        this.price = price;
        this.authorList = authorList;
    }
}
