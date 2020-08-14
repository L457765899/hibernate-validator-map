package com.sxb.lin.hibernate.validator.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HibernateValidatorApplication implements CommandLineRunner, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(HibernateValidatorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HibernateValidatorApplication.class, args);
    }

    public void destroy() throws Exception {
        logger.info("test stop-------------------------------------------------------------");
    }

    public void run(String... args) throws Exception {
        logger.info("test start-------------------------------------------------------------");
    }
}
