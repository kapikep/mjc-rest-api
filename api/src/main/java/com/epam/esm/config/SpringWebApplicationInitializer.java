package com.epam.esm.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


/**
 * Register a DispatcherServlet and use Java-based Spring configuration.
 * Instead of web.xml
 *
 * @author Artsemi Kapitula
 * @version 1.0
 */
public class SpringWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     * Specify @Configuration and/or @Component classes for the root application context.
     *
     * @return the configuration for the root application context,
     * or null if creation and registration of a root context is not desired
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    /**
     * Specify @Configuration and/or @Component classes for the Servlet application context.
     *
     * @return the configuration for the Servlet application context
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { ApiConfig.class };
    }

    /**
     * Specify the servlet mapping(s) for the DispatcherServlet — for example "/", "/app", etc.
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * Create a DispatcherServlet (or other kind of FrameworkServlet-derived dispatcher)
     * with the specified WebApplicationContext.
     */
    @Override
    protected FrameworkServlet createDispatcherServlet(WebApplicationContext wac) {
        DispatcherServlet ds = new DispatcherServlet(wac);
        ds.setThrowExceptionIfNoHandlerFound(true);
        return ds;
    }
}
