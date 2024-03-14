package com.estsoft.blogjpa.repository;

import com.estsoft.blogjpa.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Article,Long> {

//    Article findByTitle(String title);
//
//    void deleteByContent(String content);


    //직접 update 쿼리문을 작성해 결과를 수정할 수 도 있음
    // JPQL = Java Persistence Query Language   id, title
    //변경에 대한 쿼리
    @Modifying
    @Query("update Article set title = :title where id = :id")
    void updateTitle(Long id, String title);

}
