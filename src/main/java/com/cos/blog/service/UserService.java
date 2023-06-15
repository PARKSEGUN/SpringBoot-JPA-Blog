package com.cos.blog.service;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;



    @Transactional
    public void 회원가입(User user) {
        String rawPassword=user.getPassword();  //원문
        String encPassword = encoder.encode(rawPassword);   //해쉬
        user.setPassword(encPassword);
        user.setRole(RoleType.USER);
        userRepository.save(user);
    }

    @Transactional
    public void 회원수정(User user) {
        //수정은 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
        User persistanceUser = userRepository.findById(user.getId()).orElseThrow(() -> {
            return new IllegalArgumentException("회원 찾기 실패");
        });
        if (persistanceUser.getOauth() == null || persistanceUser.getOauth() == "") {
            String encodePassword = encoder.encode(user.getPassword());
            persistanceUser.setPassword(encodePassword);
            persistanceUser.setEmail(user.getEmail());
        }




        //영속화된 persistance 객체의 변화가 감지되면 더티체킹돼서 자동으로 update 문을 날려준다
        //따로 save함수를 사용하지 않아도된다 @Transactional 사용

        //세션등록(직접 AuthenticationManager 객체를 갖고와서 처리한다)




    }

    @Transactional(readOnly = true)
    public User 회원찾기(String username) {
        User user = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });
        return user;
    }
}
