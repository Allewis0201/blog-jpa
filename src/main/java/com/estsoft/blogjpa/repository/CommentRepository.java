package com.estsoft.blogjpa.repository;


import com.estsoft.blogjpa.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    
    //단순한 select문은 쿼리 어노테이션을 쓰지 않아도 알아서 잘 만들어줌
    
    //코멘트 한 개 불러오기
    //@Query("SELECT c FROM Comment c WHERE c.article.id = :articleId AND c.id = :commentId")
    Comment findByIdAndArticleId(Long articleId, Long commentId);

    //코멘트들 불러오기
//    @Query("SELECT c FROM Comment c where c.article.id = :articleId")
    List<Comment> findAllByArticleId(Long articleId);
}
     
