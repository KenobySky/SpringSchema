package com.winter.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.winter.model.login.Usuario;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.AbstractDataSource;

public class UserSchemaAwareRoutingDataSource extends AbstractDataSource {

    @Autowired
    private UsuarioProvider usuarioProvider;

    @Autowired // This references the primary datasource, because no qualifier is given
    @Qualifier(value = "companyDependentDataSource") 
    private DataSource companyDependentDataSource;

    @Autowired
    @Qualifier(value = "loginDataSource") 
    private DataSource loginDataSource;

    @Autowired
    Environment env;

    private LoadingCache<String, DataSource> dataSources = createCache();

    public UserSchemaAwareRoutingDataSource() {
       
    }



    private LoadingCache<String, DataSource> createCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, DataSource>() {
                    public DataSource load(String key) throws Exception {
                        return buildDataSourceForSchema(key);
                    }
                });
    }

    private DataSource buildDataSourceForSchema(String schema) {
        System.out.println("schema:" + schema);
        
        String url = env.getRequiredProperty("spring.datasource.url");
        //url = url.replace("/winter_web", "");
        //url = url + "/" + schema;
        
        String username = env.getRequiredProperty("spring.datasource.username");
        String password = env.getRequiredProperty("spring.datasource.password");


        DataSource build = (DataSource) DataSourceBuilder.create()
                .driverClassName(env.getRequiredProperty("spring.datasource.driver-class-name"))
                .username(username)
                .password(password)
                .url(url)
                .build();

        return build;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    private DataSource determineTargetDataSource() {
        try {
            Usuario usuario = usuarioProvider.customUserDetails(); // request scoped answer!
            String db_schema = usuario.getTunnel().getDb_schema();
            return dataSources.get(db_schema);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return super.createConnectionBuilder();
    }

}
