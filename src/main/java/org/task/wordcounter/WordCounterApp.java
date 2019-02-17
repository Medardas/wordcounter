package org.task.wordcounter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.task.wordcounter.service.FileStorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAsync
public class WordCounterApp {

    public static void main(String[] args) {
        SpringApplication.run(WordCounterApp.class, args);
    }

    @Bean
    CommandLineRunner init(FileStorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }
}
