package com.backend.jobland.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Contoller {

    @GetMapping("/")
    public String check() {
        return "Jobland Backend API";
    }

}
