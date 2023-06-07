package com.cos.blog.repository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//DAO
//자동으로 bean으로 등록이 된다
//@Repository //생략 가능하다
@Repository

public interface BoardRepository extends JpaRepository<Board, Integer> {


}
