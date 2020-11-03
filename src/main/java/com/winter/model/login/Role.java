package com.winter.model.login;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author 
 */
@Entity
@Table(name="roles")
public class Role implements GrantedAuthority{

    @Id
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }
    
    
    
    
    @Override
    public String getAuthority() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
    
}
