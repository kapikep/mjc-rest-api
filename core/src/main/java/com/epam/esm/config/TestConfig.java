package com.epam.esm.config;

import com.epam.esm.repository.impl.MySQLGiftCertificateRepository;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@PropertySource("classpath:MySQLdb.properties")
@ComponentScan(basePackages = "com.epam.esm")
public class TestConfig {

    @Bean
    public DataSource dataSource(@Value("${db.driver.name}") String driverName) throws PropertyVetoException {
        System.out.println(driverName);
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverName);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/gift_certificates");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setInitialPoolSize(10);
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
