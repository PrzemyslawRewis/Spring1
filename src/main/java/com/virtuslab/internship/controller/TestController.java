package com.virtuslab.internship.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/XD")
    public String welcomepage() {
        return "Hello World";
    }
}
