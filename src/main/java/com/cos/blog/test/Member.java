package com.cos.blog.test;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    //DB에서 들고온 값을 변경할 일이 없기때문에 final로 설정
    private  int id;
    private  String username;
    private  String password;
    private  String email;

    @Builder
    public Member(String username, String password, String email) {

        this.username = username;
        this.password = password;
        this.email = email;
    }
}
