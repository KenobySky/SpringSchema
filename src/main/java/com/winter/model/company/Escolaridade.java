package com.winter.model.company;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "escolaridade")
public class Escolaridade implements Serializable {

    @Id
    @GeneratedValue
    @SequenceGenerator(name = "escolaridade", sequenceName = "")
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "escolaridade", updatable = true, nullable = true)
    private String escolaridade;

    public Escolaridade(int id, String escolaridade) {
        this.id = id;
        this.escolaridade = escolaridade;
    }

    public Escolaridade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEscolaridade() {
        return escolaridade;
    }

    public void setEscolaridade(String escolaridade) {
        this.escolaridade = escolaridade;
    }

    @Override
    public String toString() {
        return escolaridade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.escolaridade);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Escolaridade other = (Escolaridade) obj;
        if (!Objects.equals(this.escolaridade, other.escolaridade)) {
            return false;
        }
        return true;
    }

}
