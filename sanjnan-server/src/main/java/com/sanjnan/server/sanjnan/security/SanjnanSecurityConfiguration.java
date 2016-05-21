/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan.security;

import com.sanjnan.server.enums.Role;
import com.sanjnan.server.sanjnan.security.filters.SanjnanAuthenticationFilter;
import com.sanjnan.server.sanjnan.security.provider.SanjnanTokenAuthenticationProvider;
import com.sanjnan.server.sanjnan.security.provider.SanjnanUsernamePasswordAuthenticationProvider;
import com.sanjnan.server.pojos.constants.H2OConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by vinay on 1/27/16.
 */

@Configuration
@EnableWebSecurity
@EnableScheduling
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SanjnanSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(h2oUsernamePasswordAuthenticationProvider()).
        authenticationProvider(tokenAuthenticationProvider());
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(H2OConstants.V6_SETUP_ENDPOINT);
    web.ignoring().antMatchers("/manage/health");
    web.ignoring().antMatchers("/swagger-ui.html");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable().
        sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
        and().
        authorizeRequests().
            antMatchers(actuatorEndpoints()).hasRole(Role.ADMIN.getValue()).
        anyRequest().authenticated().
        and().
        anonymous().disable().
        exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

    http.addFilterBefore(new SanjnanAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
  }

  private String[] actuatorEndpoints() {
    return new String[]{
            H2OConstants.AUTOCONFIG_ENDPOINT,
            H2OConstants.BEANS_ENDPOINT,
            H2OConstants.CONFIGPROPS_ENDPOINT,
            H2OConstants.ENV_ENDPOINT,
            H2OConstants.MAPPINGS_ENDPOINT,
            H2OConstants.METRICS_ENDPOINT,
            H2OConstants.SHUTDOWN_ENDPOINT
    };
  }

  @Bean
  public AuthenticationEntryPoint unauthorizedEntryPoint() {
    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
  }

  @Bean
  public AuthenticationProvider h2oUsernamePasswordAuthenticationProvider() {
    return new SanjnanUsernamePasswordAuthenticationProvider();
  }

  @Bean
  public AuthenticationProvider tokenAuthenticationProvider() {
    return new SanjnanTokenAuthenticationProvider();
  }

}