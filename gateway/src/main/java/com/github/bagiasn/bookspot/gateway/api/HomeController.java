package com.github.bagiasn.bookspot.gateway.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String getHome() {
        return "Hello";
    }
}
