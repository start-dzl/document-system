package com.meiya.uploader.documentsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.meiya.uploader.documentsystem.core.mapper")
public class DocumentSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentSystemApplication.class, args);
    }

}
