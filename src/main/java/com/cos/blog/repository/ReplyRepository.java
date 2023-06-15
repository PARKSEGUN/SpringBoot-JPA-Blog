package com.cos.blog.repository;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Reply;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value = "INSERT INTO reply(userId,boardId,content,createDate) VALUES (?1,?2,?3,now())",nativeQuery = true)  //대입 순서 주의
    int mSave(int userId,int boardId,String content);   //업데이트된 행의 개수를 리턴해줌

}
