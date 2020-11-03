package com.winter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPage {

    @GetMapping("/index")
    public ModelAndView listar() {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }

	
}
