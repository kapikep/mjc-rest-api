package com.epam.esm.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
/**
 * Spring configuration for Core module (dev config)
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
@Profile("dev")
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class ServiceDevConfig {

}

