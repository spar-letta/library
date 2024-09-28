package com.javenock.book_service.batch;

import com.javenock.book_service.model.Kitabu;
import com.javenock.book_service.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class KitabuItemWriter implements ItemWriter<Kitabu>, InitializingBean {

    @Autowired
    private BookService bookService;

    @Override
    public void write(Chunk<? extends Kitabu> boolList) throws Exception {
        boolList.forEach(item -> {
            bookService.createBookFromCsv(item);
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
