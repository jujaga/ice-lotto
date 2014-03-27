package com.jrfom.icelotto.config;

import com.jrfom.icelotto.security.AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth)
    throws Exception
  {
    auth.inMemoryAuthentication()
      .withUser("user").password("password").roles("USER")
      .and()
      .withUser("admin").password("admin").roles("USER", "ADMIN");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // Override the default which also alows HTTP BASIC auth (boo! hiss!)
    httpSecurity
      .authorizeRequests()
        .antMatchers("/bootstrap*/**").permitAll()
        .antMatchers("/jquery/**").permitAll()
        .antMatchers("/local/css/**").permitAll()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/**").hasRole("USER") // block all other unauthed requests
      .and()
      .formLogin()
        .loginPage("/login")
        .permitAll()
        .successHandler(new AuthSuccessHandler());
  }
}