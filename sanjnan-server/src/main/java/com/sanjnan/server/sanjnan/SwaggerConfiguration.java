package com.sanjnan.server.sanjnan;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by vinay on 2/23/16.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration extends WebMvcConfigurerAdapter {

}
