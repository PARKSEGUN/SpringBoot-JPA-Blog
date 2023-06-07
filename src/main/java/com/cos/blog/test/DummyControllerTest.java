package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {
    @Autowired  //의존성 주입
    private UserRepository userRepository;

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다!";
        }


        return "삭제되었습니다. ID : "+id;
    }

    @Transactional  //함수가 종료되면 자동으로 commit, 영속화된 데이터이 변경되었는지 확인한후 변경되었다면(변경 감지) 자동으로 DB에 수정을 보낸다 -> 더티 체킹
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
        System.out.println("id = " + id + ", requestUser = " + requestUser);
        requestUser.setId(id);
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
        //userRepository.save(user);
        return user;
    }
    //한페이지당 두건의 데이터를 리턴받기
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<User> pageUsers = userRepository.findAll(pageable);
        List<User> users = pageUsers.getContent();
        return users;
    }
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        User user=userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 없습니다. ID : " + id);
            }
        });
        //요청 : 웹브라우저
        //User 객체 = 자바 오브젝트
        //변환(웹브라우저가 이해할 수 있는 데이터) -> JSON
        //스프링 부트 는 MessageConverter라는 애가 응답시에 자동 작동
        //자바 오브젝트를 리턴하면 MessageConverter가 Jackson 라이브러리를 호출해서
        //User 오브젝트를 json으로 변환해서 전송
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println(user.toString());
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다!";
    }
}
