package com.userapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class Controller {

    @GetMapping
    public String home(){
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logout(){
        return "index";
    }
}
