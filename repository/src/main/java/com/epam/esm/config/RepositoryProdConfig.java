package com.epam.esm.config;

//import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * Spring configuration for Core module (prod config)
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Profile("prod")
@Configuration
//@PropertySource("classpath:MySQLdb.properties")
//@PropertySource("classpath:application.properties")
//@ComponentScan(basePackages = "com.epam.esm")
@EnableTransactionManagement
public class RepositoryProdConfig {

//    @Bean
//    public DataSource dataSource(@Value("${db.driver.name}") String driverName,
//                                 @Value("${db.url}") String url,
//                                 @Value("${db.user}") String user,
//                                 @Value("${db.password}") String password,
//                                 @Value("${db.pool.size}") Integer poolSize) throws PropertyVetoException {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(driverName);
//        dataSource.setJdbcUrl(url);
//        dataSource.setUser(user);
//        dataSource.setPassword(password);
//        dataSource.setMaxPoolSize(poolSize);
//        return dataSource;
//    }
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    @Bean
//    public DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
}
