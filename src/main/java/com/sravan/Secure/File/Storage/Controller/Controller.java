package com.sravan.Secure.File.Storage.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/home")
    public String hello(){
        return "Hi User";
    }
}
