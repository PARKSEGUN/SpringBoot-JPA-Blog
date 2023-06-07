package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//인증이 안된 사용자들이 출입가능한 경로/auth/~~
//그냥 주소가 / 이면 index.html 허용
//static 이하에 있는 문서는 허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "joinForm";
    }
    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "loginForm";
    }
}
