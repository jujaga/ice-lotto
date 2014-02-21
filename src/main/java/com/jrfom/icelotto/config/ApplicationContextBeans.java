package com.jrfom.icelotto.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import com.github.mxab.thymeleaf.extras.dataattribute.dialect.DataAttributeDialect;
import com.jrfom.gw2.ApiClient;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
@EnableJpaRepositories("com.jrfom.icelotto.repository")
@EnableTransactionManagement
@PropertySources({
  @PropertySource("classpath:application-${spring.profiles.active}.properties")
})
public class ApplicationContextBeans {
  @Autowired
  private Environment env;

  @Autowired
  private DataSourceConfig dataSourceConfig;

  @Bean
  public ServletContextTemplateResolver templateResolver() {
    ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
    resolver.setPrefix("/WEB-INF/templates/");
    resolver.setSuffix(".html");
    resolver.setTemplateMode("HTML5");

    // We need to see the latest and greatest during development.
    if (this.isDevEnvironment()) {
      resolver.setCacheable(false);
    }

    // Fragment HTML files can reside in the same directory as the template
    // in which they are used, but if they are stored elsewhere, you can't
    // reference them directly. Instead of cluttering the templates directory
    // with a bunch of small fragment files, I've opted to add fragments in
    // this manner.
    resolver.addTemplateAlias("item", "fragments/item");

    return resolver;
  }

  @Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setTemplateResolver(this.templateResolver());
    engine.addDialect(new DataAttributeDialect());

    return engine;
  }

  @Bean
  public ThymeleafViewResolver thymeleafViewResolver() {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(this.templateEngine());

    return viewResolver;
  }

  @Bean
  public ApiClient apiClient() {
    return new ApiClient();
  }

  @Bean
  public JpaTransactionManager transactionManager() throws ClassNotFoundException {
    JpaTransactionManager manager = new JpaTransactionManager();
    manager.setEntityManagerFactory(this.entityManagerFactory());
    return manager;
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() throws ClassNotFoundException {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(this.dataSourceConfig.dataSource());
    factoryBean.setPackagesToScan(this.env.getRequiredProperty("packages.to.scan"));
    factoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);

    Properties jpaProperties = new Properties();
    jpaProperties.put("hibernate.dialect", this.env.getRequiredProperty("hibernate.dialect"));
    jpaProperties.put("hibernate.default_schema", this.env.getRequiredProperty("hibernate.default_schema"));
    jpaProperties.put("hibernate.format_sql", this.env.getRequiredProperty("hibernate.format_sql"));
    jpaProperties.put("hibernate.ejb.naming_strategy", this.env.getRequiredProperty("hibernate.ejb.naming_strategy"));
    jpaProperties.put("hibernate.show_sql", this.env.getRequiredProperty("hibernate.show_sql"));
    jpaProperties.put("hibernate.hbm2ddl.auto", this.env.getProperty("hibernate.hbm2ddl.auto"));
    jpaProperties.put("hibernate.hbm2ddl.import_files", this.env.getProperty("hibernate.hbm2ddl.import_files"));
    jpaProperties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");

    factoryBean.setJpaProperties(jpaProperties);
    factoryBean.afterPropertiesSet();

    return factoryBean.getObject();
  }

  private boolean isDevEnvironment() {
    boolean result = false;

    String[] activeProfiles = this.env.getActiveProfiles();
    for (String profile : activeProfiles) {
      if (profile.equals("dev")) {
        result = true;
        break;
      }
    }

    return result;
  }
}