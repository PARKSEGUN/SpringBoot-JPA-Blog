package com.cos.blog.repository;

import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;

//DAO
//자동으로 bean으로 등록이 된다
//@Repository //생략 가능하다
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    //네이밍 쿼리
    Optional<User> findByUsername(String username);
}





//JPA  네이밍 쿼리
// User findByUsernameAndPassword(String username, String password);

//또는
//    @Query(value = "SELECT * FROM USER WHERE USERNAME=? AND PASSWORD = ?",nativeQuery = true)
//    User login(String username, String password);
//
