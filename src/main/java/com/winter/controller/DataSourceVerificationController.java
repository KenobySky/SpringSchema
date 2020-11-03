package com.winter.controller;


import com.winter.model.login.Usuario;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSourceVerificationController {
    
    @Autowired
    private Usuario usuario;
    
//    @Autowired
//    private EscolaridadeDAO escolaridade;
    
    @Autowired
    @Qualifier("companyDependentDataSource") // omit this annotation if you use @Primary
    private DataSource companyDependentDataSource;

    @GetMapping("/test")
    public String test() throws Exception {
        String schema = usuario.getTunnel().getDb_schema();
    
        String toString = companyDependentDataSource.getConnection().toString();
        
        
        return toString;
    }
}
