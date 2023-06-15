package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false,length=100)
    private String title;

    @Lob    //대용량 데이터
    private String content; //섬머노트 라이브러리 사용<html>태그가 섞여서 디자인 된다

    private  int count;

    @CreationTimestamp
    private Timestamp createDate;

    @OrderBy("id desc") //replyList를  가져올때 내림차순으로 정렬해서 가져온다
    @JsonIgnoreProperties({"board"})    //무한참조의 발생을 막기위해서 Reply의 board 속성을 막는다
    @OneToMany(mappedBy = "board" ,fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)   //보드를 지우면 댓글을 날려버리겠다는 cascade 설정
    //기본적략은 LAZY이지만 꼭 가지고와야하는 프로젝트를 구성이기때문에 EAGER로 변경해준다
    //@OneToMany 의 전략은 기본적으로 LAZY 이다 이유는 여러개를 들고 와야하기때문에 필요할때만 가져온다
    //mappedBy 연관관계의 주인이 아니다(난 FK가 아니니까 DB에 컬럼을 만들지마세요)
    //난 그냥 board를 select할때 join 문을 통해서 값을 얻기위해 필요한 존재입니다
    private List<Reply> replyList;

    @ManyToOne(fetch = FetchType.EAGER)  //Many = Board, User = One
    @JoinColumn(name = "userId")
    private User user; //DB는 오브젝트를 저장할 수 없다. FK 사용한다, 하지만 자바는 오브젝트를 저장할 수 있다.
}
