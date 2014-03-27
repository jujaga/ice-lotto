package com.jrfom.icelotto.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class ApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  private static final Logger log = LoggerFactory.getLogger(ApplicationInitializer.class);

  private final String SERVLET_NAME = "ice-lotto";

  @Override
  protected Class<?>[] getRootConfigClasses() {
    log.info("Getting root config classes");
    return new Class<?>[] {
      SecurityConfig.class
    };
  }

  @Override
  protected Class<?>[] getServletConfigClasses() {
    log.info("Getting servlet config classes");
    return new Class<?>[] {
      ApplicationContextConfig.class
    };
  }

  @Override
  protected Filter[] getServletFilters() {
    log.info("Getting servlet filters");
    return null;
  }

  @Override
  protected String[] getServletMappings() {
    log.info("Getting servlet mappings");
    return new String[] { "/" };
  }

  @Override
  protected String getServletName() {
    return this.SERVLET_NAME;
  }

  @Override
  public void onStartup(ServletContext context) throws ServletException {
    super.onStartup(context);

    context.addListener(new ContextListener());

    String activeProfile = System.getProperty("application.profile");
    if (activeProfile == null) {
      activeProfile = "prod";
    }

    context.setInitParameter("spring.profiles.active", activeProfile);
  }
}