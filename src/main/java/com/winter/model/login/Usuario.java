package com.winter.model.login;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name = "Usuario")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @SequenceGenerator(name = "usuario", sequenceName = "usuarios")
    @Column(name = "idUsuario", updatable = false, nullable = false)
    private int idUsuario;

    @Column(name = "nomeUsuario", updatable = false, nullable = false)
    private String username;

    @Column(name = "nomeCompleto", updatable = true, nullable = false)
    private String nomeCompleto;

    @Column(name = "senha", updatable = true, nullable = false)
    private String senha;
    
    @Column(name = "email", updatable = true, nullable = true)
    private String email;

    private boolean allowAccess = true;

    @ManyToOne()
    @JoinColumn(name = "idTunnel", nullable = false, updatable = false)
    private Tunnel tunnel;

    //
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    /**
     *
     */
    public Usuario() {
        roles = new ArrayList<>();
    }

    public boolean isAllowAccess() {
        return allowAccess;
    }

   

    public void setAllowAccess(boolean allowAccess) {
        this.allowAccess = allowAccess;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomeUsuario() {
        return username;
    }

    public void setNomeUsuario(String username) {
        this.username = username;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    

    @Override
    public String toString() {
        return nomeCompleto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAllowAccess();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAllowAccess();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isAllowAccess();
    }

    @Override
    public boolean isEnabled() {
        return isAllowAccess();
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

}
