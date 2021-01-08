package com.example.jwt.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private JwtFilter jwtFilter;


  public WebSecurity(JwtFilter jwtFilter) {
    this.jwtFilter = jwtFilter;


  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/users").hasRole("USER")
        .antMatchers("/user/{username}").hasRole("USER")
        .and()
        .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    http.headers().frameOptions().disable();

  }


  @Bean
  public PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

}
