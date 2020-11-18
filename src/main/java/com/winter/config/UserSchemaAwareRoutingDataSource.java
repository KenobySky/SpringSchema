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

/**
 *
 *
 */
public class UserSchemaAwareRoutingDataSource extends AbstractDataSource {

    @Autowired
    private UsuarioProvider usuarioProvider;

    /**
     * This is the data source that is dependent on the user.
     */
    @Autowired
    @Qualifier(value = "companyDependentDataSource")
    private DataSource companyDependentDataSource;

    /**
     * This is the initial datasource.
     */
    @Autowired
    @Qualifier(value = "loginDataSource")
    private DataSource loginDataSource;

    /**
     * Variable representing the environment in which the current application is
     * running.
     */
    @Autowired
    Environment env;

    /**
     * A semi-persistent mapping from Schemas to dataSources. This exists,
     * because ??? to increase performance and diminish overhead???
     */
    private LoadingCache<String, DataSource> dataSources = createCache();

    public UserSchemaAwareRoutingDataSource() {

    }

    /**
     * Creates the cache. ???
     *
     * @return
     */
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

    /**
     * Builds the datasource with the schema parameter. Notice that the other
     * parameters are fixed by the application.properties.
     *
     * @param schema
     * @return
     */
    private DataSource buildDataSourceForSchema(String schema) {
        logger.info("building datasource with schema " + schema);

        String url = env.getRequiredProperty("companydatasource.url");

        String username = env.getRequiredProperty("companydatasource.username");
        String password = env.getRequiredProperty("companydatasource.password");

        DataSource build = DataSourceBuilder.create()
                .driverClassName(env.getRequiredProperty("companydatasource.driver-class-name"))
                .username(username)
                .password(password)
                .url(url)
                .build();

        return build;
    }

    /**
     * Gets the Schema from the Cache, or build one if it doesnt exist.
     *
     * @return
     */
    private DataSource determineTargetDataSource() {
        try {
            String db_schema = determineTargetSchema();
            logger.info("using schema " + db_schema);
            return dataSources.get(db_schema);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Determine the schema based on the logger-in User.
     *
     * @return
     */
    private String determineTargetSchema() {
        try {
            Usuario usuario = usuarioProvider.customUserDetails(); // request scoped answer!
            return usuario.getTunnel().getDb_schema();
        } catch (RuntimeException e) {
            // This shouldn't be necessary, since we are planning to use a pre-initialized database.
            // And there should only be usages of this DataSource in a logged-in situation
            logger.info("usuario not present, falling back to default schema", e);
            return "default_company_schema";
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return determineTargetDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return determineTargetDataSource().getConnection(username, password);
    }

    @Override
    public ConnectionBuilder createConnectionBuilder() throws SQLException {
        return super.createConnectionBuilder();
    }

}
