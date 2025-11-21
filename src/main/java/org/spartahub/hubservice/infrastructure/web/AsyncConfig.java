package org.spartahub.hubservice.infrastructure.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name="simpleTaskExecutor")
    public SimpleAsyncTaskExecutor simpleAsyncTaskExecutor(){
        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(5); // 최대 5개 동시 실행 허용
        return executor;
     }
}
