package com.javenock.book_service.service;

import org.springframework.batch.core.JobExecution;

import java.util.Optional;

public interface BatchJobService {
    Optional<JobExecution> processKitabuImport();
}
