package com.jrfom.icelotto.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.WebContentInterceptor;

@Configuration
@EnableWebMvc
@ComponentScan (
  basePackages = (
    "com.jrfom"
  )
)
public class ApplicationContextConfig extends WebMvcConfigurerAdapter {
  private static final Logger log = LoggerFactory.getLogger(ApplicationContextConfig.class);

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // Disable caching for a specific application path.
    WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
    webContentInterceptor.setUseCacheControlHeader(true);
    webContentInterceptor.setCacheSeconds(0);
    webContentInterceptor.setUseCacheControlNoStore(true);
    webContentInterceptor.setUseCacheControlHeader(true);
    registry.addInterceptor(webContentInterceptor)
        .addPathPatterns("/**");

    super.addInterceptors(registry);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("local/**")
      .addResourceLocations("/WEB-INF/assets/local/");

    registry
      .addResourceHandler("bootstrap/**")
      .addResourceLocations("/WEB-INF/assets/bootstrap-3.1.0/");

    registry
      .addResourceHandler("jquery/**")
      .addResourceLocations("/WEB-INF/assets/jquery-2.1.0/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    super.addViewControllers(registry);
  }
}