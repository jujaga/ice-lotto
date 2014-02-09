package com.jrfom.icelotto.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.sqlite.SQLiteDataSource;

@Configuration
@Profile("prod")
@PropertySources({
  @PropertySource("classpath:application-prod.properties")
})
public class ProdDataSouceConfig implements DataSourceConfig {
  @Autowired
  private Environment env;

  public DataSource dataSource() {
    SQLiteDataSource dataSource = new SQLiteDataSource();
    dataSource.setDatabaseName(this.env.getRequiredProperty("db.name"));
    dataSource.setUrl(this.env.getRequiredProperty("db.url"));

    return dataSource;
  }
}