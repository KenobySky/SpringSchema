package com.winter.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 *
 * Set up the Data Source Configuration.
 * This class Initializes the beans: loginDataSource and companyDependentDataSource;
 */
@Configuration
public class DataSourceConfiguration {

    /**
     * Uses the default setting from the application.properties.
     * @param env
     * @return 
     */
    @Bean(name = "loginDataSource")
    public DataSource loginDataSource(Environment env) {
        String url = env.getRequiredProperty("spring.datasource.url");
        String classname = env.getRequiredProperty("spring.datasource.driver-class-name");

        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");

        return DataSourceBuilder.create()
                .driverClassName(classname)
                .username(username)
                .password(password)
                .url(url)
                .build();
    }

    /**
     * 
     * @param env
     * @return 
     */
    @Bean(name = "companyDependentDataSource")
    public DataSource companyDependentDataSource(Environment env) {
        return new UserSchemaAwareRoutingDataSource(); // Autowiring is done afterwards by Spring
    }
}
