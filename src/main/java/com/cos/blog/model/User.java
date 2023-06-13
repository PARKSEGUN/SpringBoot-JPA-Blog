package com.cos.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@DynamicInsert  //insert 시에 null 인 값을 제외시켜준다
public class User {
    @Id //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
    private int id; //auto_increment

    @Column(nullable = false,length = 100)
    private String username;    //아이디
    @Column(nullable = false,length = 100)
    private String password;
    @Column(nullable = false,length = 50)
    private String email;


    //@ColumnDefault("'user'")
    //DB에는 RoleType이라는게 없다 따라서
    @Enumerated(EnumType.STRING)    //해당 Enum이 스트링이 라고 알려준다
    private RoleType role;    //Enum을 쓰는게 좋다


    @CreationTimestamp  //시간이 자동으로 입력
    private Timestamp createDate;
}
