package com.partymakers.shareparty;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/info")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name){
        return "Hello World!";
    }
}
