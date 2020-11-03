package com.winter.repository.login;


import com.winter.model.login.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    Usuario findByUsername(String username);

}
