package com.jrfom.icelotto.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.h2.jdbcx.JdbcDataSource;
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
    String dbType = this.env.getRequiredProperty("db.type");
    DataSource dataSource = null;

    if (dbType.toLowerCase().equals("h2")) {
      dataSource = new JdbcDataSource();
      ((JdbcDataSource) dataSource).setUrl(this.env.getRequiredProperty("db.url"));
    } else if (dbType.toLowerCase().equals("sqlite")) {
      dataSource = new SQLiteDataSource();
      ((SQLiteDataSource) dataSource).setDatabaseName(this.env.getRequiredProperty("db.name"));
      ((SQLiteDataSource) dataSource).setUrl(this.env.getRequiredProperty("db.url"));
      ((SQLiteDataSource) dataSource).setEncoding("UTF-8");
    }

    return dataSource;
  }

  @Override
  public Properties jpaProperties() {
    Properties jpaProperties = new Properties();

    jpaProperties.put("hibernate.dialect", this.env.getRequiredProperty("hibernate.dialect"));
    jpaProperties.put("hibernate.format_sql", this.env.getRequiredProperty("hibernate.format_sql"));
    jpaProperties.put("hibernate.ejb.naming_strategy", this.env.getRequiredProperty("hibernate.ejb.naming_strategy"));
    jpaProperties.put("hibernate.show_sql", this.env.getRequiredProperty("hibernate.show_sql"));
    jpaProperties.put("hibernate.hbm2ddl.auto", this.env.getProperty("hibernate.hbm2ddl.auto"));
    jpaProperties.put("hibernate.hbm2ddl.import_files", this.env.getProperty("hibernate.hbm2ddl.import_files"));
    jpaProperties.put("hibernate.hbm2ddl.import_files_sql_extractor", "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");

    jpaProperties.put("hibernate.connection.CharSet", "utf8");
    jpaProperties.put("hibernate.connection.characterEncoding", "utf8");
    jpaProperties.put("hibernate.connection.useUnicode", "true");

    // H2 database needs this next one
    String schema = this.env.getProperty("hibernate.default_schema");
    if (schema != null) {
      jpaProperties.put("hibernate.default_schema", schema);
    }

    return jpaProperties;
  }
}