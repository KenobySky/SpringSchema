package com.winter.model.login;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "Tunnel")
public class Tunnel {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "Tunnel", sequenceName = "tuneis")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    //
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tunnel")
    private List<Usuario> usuarios = new ArrayList<>();

    @Column(name = "nomeEmpresa")
    private String nomeEmpresa;

    @Column(name = "db_username")
    private String db_username;

    @Column(name = "db_password")
    private String db_password;

    @Column(name = "db_url")
    private String db_url;

    @Column(name = "db_schema")
    private String db_schema;

    public Tunnel(int id, String nomeEmpresa, String db_username, String db_password, String db_url, String schema) {
        this.id = id;
        this.nomeEmpresa = nomeEmpresa;
        this.db_username = db_username;
        this.db_password = db_password;
        this.db_url = db_url;
        this.db_schema = schema;
    }

    public Tunnel() {
    }

    public int getId() {
        return id;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public String getDb_schema() {
        return db_schema;
    }

    public void setDb_schema(String db_schema) {
        this.db_schema = db_schema;
    }

    

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    @Override
    public String toString() {
        return nomeEmpresa;
    }


}
