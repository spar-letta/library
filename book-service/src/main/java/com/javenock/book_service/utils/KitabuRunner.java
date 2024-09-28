package com.javenock.book_service.utils;

import com.javenock.book_service.service.BatchJobService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class KitabuRunner implements CommandLineRunner {
    private final BatchJobService batchJobService;

    public KitabuRunner(BatchJobService batchJobService) {
        this.batchJobService = batchJobService;
    }

    @Override
    public void run(String... args) throws Exception {
//        batchJobService.processKitabuImport();

    }
}
