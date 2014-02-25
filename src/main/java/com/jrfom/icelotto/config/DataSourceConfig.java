package com.jrfom.icelotto.config;


import java.util.Properties;

import javax.sql.DataSource;

public interface DataSourceConfig {
  DataSource dataSource();
  Properties jpaProperties();
}
