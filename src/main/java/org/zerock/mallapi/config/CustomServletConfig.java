package org.zerock.mallapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zerock.mallapi.controller.formatter.LocalDateFormatter;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addFormatter(new LocalDateFormatter());
  }

  // @Override
  // public void addCorsMappings(CorsRegistry registry) {

  // // WebMvcConfigurer.super.addCorsMappings(registry);

  // //
  // registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("GET",
  // // "POST", "PUT", "DELETE")
  // // .allowCredentials(false).maxAge(3600);

  // registry.addMapping("/**").allowedOrigins("*").allowedMethods("HEAD", "GET",
  // "POST", "PUT", "DELETE", "OPTIONS")
  // .maxAge(300).allowedHeaders("Authorization", "Content-Type", "Cache-Control",
  // "Content-Type");

  // }

}
