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
 * @author 
 */
@Configuration
public class UsuarioProvider {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS) // or just @RequestScope
    public Usuario customUserDetails() {
        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
