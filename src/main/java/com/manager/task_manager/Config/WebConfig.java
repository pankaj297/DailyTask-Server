package com.manager.task_manager.Config;

import java.nio.file.Paths;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowCredentials(true);
            }

              @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Local folder ka absolute path lo
                String imagePath = Paths.get("src/main/resources/static/image")
                        .toAbsolutePath()
                        .toUri()
                        .toString();

                // /image/** URL ko actual folder se map karo
                registry.addResourceHandler("/image/**")
                        .addResourceLocations(imagePath);
            }
            
        };
    }

    
}
