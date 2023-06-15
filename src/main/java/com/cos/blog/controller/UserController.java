package com.cos.blog.controller;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

//인증이 안된 사용자들이 출입가능한 경로/auth/~~
//그냥 주소가 / 이면 index.html 허용
//static 이하에 있는 문서는 허용

@Controller
public class UserController {


    @Value("${cos.key}")
    private String cosKey;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "joinForm";
    }
    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "loginForm";
    }
    @GetMapping("/user/updateForm")
    public String updateForm(@AuthenticationPrincipal PrincipalDetail principalDetail, Model model) {
        model.addAttribute("user", principalDetail.getUser());
        return "updateForm";
    }

    //@ResponseBody를 쓰면 Data를 리턴해주는 컨트롤러 함수가된다
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        //POST방식으로 KEY-VALUE 데이터를 요청 (카카오쪽으로)
        //여러가지 라이브러리가 있지만 RestTemplate를 사용
        RestTemplate restTemplate = new RestTemplate();
        //헤더설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");

        //바디 설정하기위한 구조
        MultiValueMapAdapter<String, String> params = new LinkedMultiValueMap<>();
        //이러한 값들이 들어간것들은 밑에 code 처럼 변수로 저장해주고 사용하자 설명을 위해서 직접 넣었다.
        params.add("grant_type","authorization_code");
        params.add("client_id","20c66978b03bcfb37f3f31f7a06a2488");
        params.add("redirect_uri","http://localhost:8000/auth/kakao/callback");
        params.add("code",code);
        //HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        //restTemplate의 exchange()함수가 HttpEntity객체를 원하기때문에 만들어준다
        HttpEntity<MultiValueMapAdapter<String, String>> kakaoTokenRequest =
                new HttpEntity<>(params, httpHeaders);
        //Http 요청하기 - Post방식으로 - response변수의 응답 받음
        ResponseEntity<String> response = restTemplate.exchange(
                "https://kauth.kakao.com/oauth/token",  //요청할 주소
                HttpMethod.POST,    //요청한 메서드 방법
                kakaoTokenRequest,  //요청할 정보
                String.class    //받아올 데이터형식
        );

        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken=null;
        try {
            oAuthToken=objectMapper.readValue(response.getBody(),OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders httpHeaders2 = new HttpHeaders();
        httpHeaders2.add("Authorization", "Bearer "+oAuthToken.getAccess_token());
        httpHeaders2.add("Content-Type" ,"application/x-www-form-urlencoded");
        //바디 설정하기위한 구조
        //restTemplate의 exchange()함수가 HttpEntity객체를 원하기때문에 만들어준다
        HttpEntity<MultiValueMapAdapter<String, String>> kakaoProfileRequest =
                new HttpEntity<>(httpHeaders2);
        //Http 요청하기 - Post방식으로 - response변수의 응답 받음
        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://kapi.kakao.com/v2/user/me",  //요청할 주소
                HttpMethod.POST,    //요청한 메서드 방법
                kakaoProfileRequest,  //요청할 정보
                String.class    //받아올 데이터형식
        );

        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile=null;
        try {
            kakaoProfile=objectMapper2.readValue(response2.getBody(),KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        User kakaoUser = User.builder()
                .username(kakaoProfile.getKakaoAccount().getEmail() + "_" + kakaoProfile.getId())
                .password(cosKey)
                .email(kakaoProfile.getKakaoAccount().getEmail())
                .oauth("kakao")
                .build();

        //무조건 회원가입이 아닌 가입자인지 비가입자인지 확인
        User originuser = userService.회원찾기(kakaoUser.getUsername());

        if(originuser.getUsername()==null){
            userService.회원가입(kakaoUser);
        }
        else{
            //spring security를 이용해서 로그인을하면 자동으로 세션이 등록되지만 지금은 카카오 로그인으로 로그인했기때문에 이전에
            //회원정보를 변경했을때에 세션을 변경해주는 방법을 사용해서 세션을 등록해준다
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), kakaoUser.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }


        return "redirect:/";
    }
}
