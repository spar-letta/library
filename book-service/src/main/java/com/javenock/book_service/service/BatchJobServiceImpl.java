package com.javenock.book_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class BatchJobServiceImpl implements BatchJobService {
    private final JobLauncher jobLauncher;
    private final Job productJob;

    public BatchJobServiceImpl(JobLauncher jobLauncher, Job productJob) {
        this.jobLauncher = jobLauncher;
        this.productJob = productJob;
    }


    @Override
    public Optional<JobExecution> processKitabuImport() {
        JobParametersBuilder builder = new JobParametersBuilder();
        builder.addDate("date", new Date());
        log.info("Start of Kitabu import Job >>>>>");

        try {
            return Optional.of(jobLauncher.run(productJob, builder.toJobParameters()));
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException |
                 JobParametersInvalidException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
