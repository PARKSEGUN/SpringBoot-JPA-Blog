package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
    @GetMapping("/html")
    public  String tempHome() {
        System.out.println("tempHome");

        return "home";
    }
}
