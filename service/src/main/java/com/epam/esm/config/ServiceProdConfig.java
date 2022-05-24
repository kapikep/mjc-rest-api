package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * Spring configuration for Core module (prod config)
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Profile("prod")
@Configuration
@PropertySource("classpath:MySQLdb.properties")
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = "com.epam.esm")
public class ServiceProdConfig {

}
