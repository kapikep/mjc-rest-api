package com.epam.esm.service;

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
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class ServiceTestConfig {

}


