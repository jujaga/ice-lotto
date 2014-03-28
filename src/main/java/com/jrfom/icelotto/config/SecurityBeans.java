package com.jrfom.icelotto.config;

import com.jrfom.icelotto.security.SqliteUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityBeans {
  @Bean
  public UserDetailsService userDetailsService() {
    return new SqliteUserDetailsService();
  }
}