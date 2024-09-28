package com.javenock.book_service.batch;

import com.javenock.book_service.model.Kitabu;
import com.javenock.book_service.model.dataTypes.BookType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public class KitabuItemProcessor implements ItemProcessor<Kitabu, Kitabu> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Kitabu process(final Kitabu kitabu) throws Exception {
        final String title = kitabu.getTitle();
        final String description = kitabu.getDescription();
        final BookType type = kitabu.getType();
        final LocalDate datePublished = LocalDate.parse(kitabu.getDatePublishedString());
        final BigDecimal price = kitabu.getPrice();
        final Set<String> authorList = kitabu.getAuthorList();  //List of names

        return new Kitabu(title, description, type, datePublished, price, authorList);
    }
}
