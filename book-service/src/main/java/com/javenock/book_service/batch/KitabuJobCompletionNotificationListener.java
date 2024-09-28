package com.javenock.book_service.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;
@Component
public class KitabuJobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            logger.info("!!! Kitabu JOB FINISHED! Time to verify the results");
        }
    }
}
