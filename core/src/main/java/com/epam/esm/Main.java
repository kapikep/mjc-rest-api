package com.epam.esm;

import com.epam.esm.config.CoreConfig;
import com.epam.esm.config.TestConfig;
import com.epam.esm.repository.impl.MySQLGiftCertificateRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CoreConfig.class)){
            MySQLGiftCertificateRepository giftCertificateRepository = context.getBean(MySQLGiftCertificateRepository.class);
            System.out.println(giftCertificateRepository);

        }
    }
}
