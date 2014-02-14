package com.jrfom.icelotto.config;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;

@Configuration
@Profile("test")
@PropertySources({
  @PropertySource("classpath:application-test.properties")
})
public class TestDataSouceConfig implements DataSourceConfig {
  @Autowired
  private Environment env;

  @Override
  public DataSource dataSource() {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl(this.env.getRequiredProperty("db.url"));

    return dataSource;
  }
}