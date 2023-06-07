package com.cos.blog.test;

import org.springframework.web.bind.annotation.*;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    private  static final String TAG="HttpControllerTest :";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member member = Member.builder().username("qkrtprjs").password("1234").email("qkrtprjs3456@naver.com").build();
        System.out.println(TAG + "getter : " + member.getId());
        member.setId(5000);
        System.out.println(TAG + "getter : " + member.getId());
        return "lombok test 완료";
    }
    //http://localhost:8080/http/get
    @GetMapping("/http/get")
    public String getTest(Member member) {

        return "get 요청 : " +member.getId()+", "+member.getUsername()+", "+member.getPassword()+", "+member.getEmail();
    }
    @PostMapping("/http/post")
    public String postTest(@RequestBody Member member) {
        return "post 요청 : " +member.getId()+", "+member.getUsername()+", "+member.getPassword()+", "+member.getEmail();
    }
    @PutMapping("/http/put")
    public String putTest(@RequestBody Member member) {
        return "put 요청 : " +member.getId()+", "+member.getUsername()+", "+member.getPassword()+", "+member.getEmail();
    }
    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
