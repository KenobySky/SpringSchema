package com.winter.controller;


import com.winter.model.company.Escolaridade;
import com.winter.model.login.Usuario;
import com.winter.repository.company.EscolaridadeDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to test.
 * Access /test
 * 
 */
@RestController
public class DataSourceVerificationController {
    
    @Autowired
    private Usuario usuario;

    @Autowired
    private EscolaridadeDAO escolaridade;

    @GetMapping("/test")
    public String test() throws Exception {
        String schema = usuario.getTunnel().getDb_schema();

        List<Escolaridade> all = escolaridade.findAll();
        return "schema: " + schema + "<br>found: " + all;
    }
}
