package com.jrfom.icelotto.config;

import com.jrfom.icelotto.security.AuthSuccessHandler;
import com.jrfom.icelotto.security.LocalPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebMvcSecurity
@Import({
  SecurityBeans.class
})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserDetailsService userDetailsService;

  public void configureGlobal(AuthenticationManagerBuilder auth)
    throws Exception
  {
    /*auth.inMemoryAuthentication()
      .withUser("user").password("password").roles("USER")
      .and()
      .withUser("admin").password("admin").roles("USER", "ADMIN");*/

    auth
      .userDetailsService(this.userDetailsService)
      .passwordEncoder(new LocalPasswordEncoder());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
      .antMatchers("/bootstrap*/**")
      .antMatchers("/jquery/**")
      .antMatchers("/*/css/**");
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    // Override the default which also alows HTTP BASIC auth (boo! hiss!)
    httpSecurity
      .authorizeRequests()
        .antMatchers("/admin/**").hasRole("ADMIN")
        .antMatchers("/**").hasRole("USER") // block all other unauthed requests
      .and()
      .formLogin()
        .loginPage("/login")
        .permitAll()
        .successHandler(new AuthSuccessHandler());
  }
}