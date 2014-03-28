package com.jrfom.icelotto.config;

import com.jrfom.icelotto.security.LocalPasswordEncoder;
import com.jrfom.icelotto.security.SqliteUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeans {
  @Bean
  public UserDetailsService userDetailsService() {
    return new SqliteUserDetailsService();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new LocalPasswordEncoder();
  }
}