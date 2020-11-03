package com.winter.config;

import java.util.HashMap;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
/**
 *
 * @author 
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.winter.repository.company",
        entityManagerFactoryRef = "companyEntityManagerFactory",
        transactionManagerRef = "companyTransactionManager"
)
public class PersistenceCompanyConfiguration {
    @Autowired
    @Qualifier("companyDependentDataSource")
    private DataSource companyDependentDataSource;
    
    @Autowired
    private Environment env;
    
    @Bean(name="companyEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(companyDependentDataSource);
        // ... see Baeldung Blog Post
      
        
        emf.setPackagesToScan(new String[]{"com.winter.model.company"});

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("hibernate.naming.physical-strategy","org.hibernate.cfg.EJB3NamingStrategy");
        emf.setJpaPropertyMap(properties);
        
        return emf;
    }

    @Bean(name="companyTransactionManager")
    public PlatformTransactionManager companyTransactionManager() {
        JpaTransactionManager tm = new JpaTransactionManager();
        tm.setEntityManagerFactory(companyEntityManagerFactory().getObject());
        return tm;
    }
}
