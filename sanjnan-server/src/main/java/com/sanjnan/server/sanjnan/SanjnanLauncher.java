/*
 * Copyright 2016 (c) Hubble Connected (HKT) Ltd. - All Rights Reserved
 *
 * Proprietary and confidential.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.sanjnan.server.sanjnan;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.sanjnan.server.config.annotations.ORMCache;
import com.sanjnan.server.sanjnan.dao.TenantDao;
import com.sanjnan.server.sanjnan.security.SanjnanAuditorAware;
import com.sanjnan.server.sanjnan.security.provider.SanjnanAuditingDateTimeProvider;
import com.sanjnan.server.sanjnan.service.DateTimeService;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCachePrefix;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by vinay on 1/8/16.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.hubbleconnected.server.sanjnan"})
@EnableAutoConfiguration
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = {"com.hubbleconnected.server.sanjnan.dao"})
//@ImportResource({"classpath*:spring/applicationContext.xml"})
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
@EnableTransactionManagement
//@EnableSwagger2
@PropertySource("classpath:/properties/redis.properties")
@EnableCaching
public class SanjnanLauncher {

  Logger logger = Logger.getLogger(SanjnanLauncher.class.getName());
  @Autowired
  private TenantDao tenantDao;

  private
  @Value("${redis.host}")
  String redisHost;
  private
  @Value("${redis.port}")
  int redisPort;
  private
  @Value("${redis.password}")
  String redisPassword;
  private
  @Value("${redis.database}")
  int redisDatabase;

  public static void main(String[] args) {
    SpringApplication.run(SanjnanLauncher.class, args);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  AuditorAware<String> auditorProvider() {
    return new SanjnanAuditorAware();
  }

  @Bean
  DateTimeProvider dateTimeProvider(DateTimeService dateTimeService) {
    return new SanjnanAuditingDateTimeProvider(dateTimeService);
  }

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setHostName(redisHost);
    factory.setPort(redisPort);
    factory.setPassword(redisPassword);
    factory.setDatabase(redisDatabase);
    factory.setUsePool(true);
    return factory;
  }

  @Bean
  RedisTemplate<Object, Object> redisTemplate() {
    RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory());
    return redisTemplate;
  }

  @Bean
  CacheManager cacheManager() {
    long sevenDays = 7 * 24 * 60 * 60;
    long oneDay = 24 * 60 * 60;
    long sixHour = 6 * 60 * 60;
    long oneHour =  60 * 60;
    long oneMinute = 60;
    RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
    populateCacheManager(cacheManager);
    cacheManager.setDefaultExpiration(7 * 24 * 60 * 60); // 7 Days
    return cacheManager;
  }

  private void populateCacheManager(RedisCacheManager cacheManager) {

    Reflections reflections = new Reflections("com.hubbleconnected.server.sanjnan.caching");

    Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(ORMCache.class);
    Map<String, Long> expiryList = new HashMap<>();
    final Map<String, byte[]> cachePrefix = new HashMap<>();
    List<String> cacheNameList = new ArrayList<>();
    for (Class<?> c : annotated) {

      String name = c.getAnnotation(ORMCache.class).name();
      long expiry = c.getAnnotation(ORMCache.class).expiry();
      cacheNameList.add(name);
      expiryList.put(name, expiry);
      cachePrefix.put(name, c.getAnnotation(ORMCache.class).prefix().getBytes());
    }
    cacheManager.setCachePrefix(new RedisCachePrefix() {

      @Override
      public byte[] prefix(final String s) {
        byte[] prefix = cachePrefix.get(s);
        return (prefix != null ? prefix : new String("").getBytes());
      }
    });
    cacheManager.setCacheNames(cacheNameList);
    cacheManager.setExpires(expiryList);
  }

  @Bean
  public Module jodaModule() {
    return new JodaModule();
  }
/*    @Bean
    RoleHierarchyImpl roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = H2OConstants.SanjnanRole.ADMIN.getValue() + " > " + H2OConstants.SanjnanRole.FW_UPGRADE_ADMIN.getValue() + " ";
        hierarchy += ()
        roleHierarchy.setHierarchy();

    }*/

}