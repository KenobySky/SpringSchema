package com.winter.config;

import com.winter.model.company.Escolaridade;
import com.winter.model.login.Tunnel;
import com.winter.model.login.Usuario;
import com.winter.repository.company.EscolaridadeDAO;
import com.winter.repository.login.UsuarioDAO;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Class That populates the Database
 */
@Slf4j
@Configuration
public class DataPopulator {

    @Autowired
    UsuarioDAO dao;

    @Autowired
    EscolaridadeDAO escolaridadeDao;

    /**
     * The PostConstruct annotation is used on a method that needs to be
     * executed after dependency injection is done to perform any
     * initialization.
     */
    @PostConstruct
    public void createDefaultUser() {
        Usuario usuario = new Usuario();
        usuario.setNomeCompleto("user name");
        usuario.setNomeUsuario("username");
        usuario.setSenha("{noop}password");

        Tunnel tunnel = new Tunnel();
        tunnel.setDb_schema("username_schema");
        usuario.setTunnel(tunnel);

        log.info("creating user {}", usuario);
        dao.save(usuario);
    }

    @PostConstruct
    public void createDefaultEscolaridade() {
        createEscolaridade("my first escolaridade");
        createEscolaridade("my second escolaridade");
    }

    private void createEscolaridade(String name) {
        Escolaridade escolaridade = new Escolaridade();
        escolaridade.setEscolaridade(name);
        log.info("creating escolaridade {}", escolaridade);
        escolaridadeDao.save(escolaridade);
    }
}
