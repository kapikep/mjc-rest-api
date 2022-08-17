package com.epam.esm.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Spring configuration for Core module (dev config)
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Profile("test")
@ComponentScan(basePackages = "com.epam.esm")
@SpringBootConfiguration
@EnableAutoConfiguration
@EntityScan(basePackages = "com.epam.esm.entity")
public class ServiceTestConfig {
}


