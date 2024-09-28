package com.javenock.book_service.configuration;

import com.javenock.book_service.batch.KitabuItemProcessor;
import com.javenock.book_service.batch.KitabuItemWriter;
import com.javenock.book_service.batch.TaskLetTest;
import com.javenock.book_service.model.Kitabu;
import com.javenock.book_service.repository.BookRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    private BookRepository bookRepository;


    //item reader
    @Bean
    public FlatFileItemReader<Kitabu> kitabuItemReader() {
        FlatFileItemReader<Kitabu> reader = new FlatFileItemReader<Kitabu>();
        reader.setResource(new ClassPathResource("Kitabu.csv"));
        reader.setLinesToSkip(1);
        reader.setLineMapper(new DefaultLineMapper<Kitabu>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[]{"title", "description", "type", "datePublishedString", "price", "authorList"});
                setDelimiter(":");
            }});

            setFieldSetMapper(new BeanWrapperFieldSetMapper<Kitabu>() {{
                setTargetType(Kitabu.class);
            }});
        }});
        return reader;
    }

    //processor
    @Bean
    public KitabuItemProcessor kitabuItemProcessor() {
        return new KitabuItemProcessor();
    }

    //writer
    @Bean
    public KitabuItemWriter kitabuItemWriter() {
        return new KitabuItemWriter();
    }

    //step
    @Bean
    public Step stepProduct(JobRepository jobRepository, PlatformTransactionManager transactionManage) {
        var step = new StepBuilder("stepProduct", jobRepository)
                .<Kitabu, Kitabu>chunk(10, transactionManage)
                .reader(kitabuItemReader())
                .processor(kitabuItemProcessor())
                .writer(kitabuItemWriter())
                .build();
        return step;
    }

    //step 2 use a tasklet
    @Bean
    public Tasklet taskletTest() {
        return new TaskLetTest();
    }


    @Bean
    public Step stepTest(JobRepository jobRepository, PlatformTransactionManager transactionManage) {
        var step = new StepBuilder("stepTest", jobRepository)
                .tasklet(taskletTest(), transactionManage)
                .build();
        return step;
    }

    //job
    @Bean
    public Job productJob(JobRepository jobRepository,
                          @Qualifier("stepProduct") Step step1,
                          @Qualifier("stepTest") Step step2) {
        return new JobBuilder("productJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .build();
    }

}
