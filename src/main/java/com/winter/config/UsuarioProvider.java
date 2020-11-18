package com.winter.config;

import com.winter.model.login.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * Set up for Spring the The identity of the principal being authenticated;
 */
@Configuration
public class UsuarioProvider {

    /**
     * 
     * https://docs.spring.io/spring-framework/docs/3.0.0.M3/reference/html/ch04s04.html
     * 
     * SCOPE_REQUEST = Per HTTP request
     * 
     * SCOPEDPROXYMODE = Create a class-based proxy (uses CGLIB)
     * 
     * @return 
     */
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS) // or just @RequestScope
    public Usuario customUserDetails() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
