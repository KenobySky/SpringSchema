package com.winter.config;

import com.winter.model.company.Escolaridade;
import com.winter.model.login.Tunnel;
import com.winter.model.login.Usuario;
import com.winter.repository.company.EscolaridadeDAO;
import com.winter.repository.login.UsuarioDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class DataPopulator {

    @Autowired
    UsuarioDAO dao;

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

    @Autowired
    EscolaridadeDAO escolaridadeDao;

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
