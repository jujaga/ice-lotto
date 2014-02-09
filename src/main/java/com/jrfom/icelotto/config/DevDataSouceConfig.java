package com.jrfom.icelotto.config;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

@Configuration
@Profile("dev")
@PropertySources({
  @PropertySource("classpath:application-dev.properties")
})
public class DevDataSouceConfig implements DataSourceConfig {
  @Autowired
  private Environment env;

  @Override
  public DataSource dataSource() {
    JdbcDataSource dataSource = new JdbcDataSource();
    dataSource.setUrl(this.env.getRequiredProperty("db.url"));

    return dataSource;
  }
}