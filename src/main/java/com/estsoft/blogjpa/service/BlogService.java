package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.external.ExampleAPIClient;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.model.Comment;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.repository.CommentRepository;
import dto.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;


    public BlogService(BlogRepository blogRepository, CommentRepository commentRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
    }

    public Article save(AddArticleRequest request)
    {
        return blogRepository.save(request.toEntity());
    }




    public List<Article> findAll()
    {
        return blogRepository.findAll();
    }

    public Article findById(Long id)
    {
        return blogRepository.findById(id).orElseThrow(()->new IllegalArgumentException("not found id" + id));
        //return blogRepository.findById(id).orElse(new Article());
    }

    public void deleteById(Long id)
    {
        blogRepository.deleteById(id);
    }

//    public String fetchDataFromApi()
//    {
//        String result = apiClient.fetchDataFromApi();
//
//        return result;
//    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request)
    {
        //begin transaction
        Article article = findById(id);
        article.update(request.getTitle(), request.getContent());

        // commit/rollback
        return article;
    }


    @Transactional
    public Article updateTitle(Long id, UpdateArticleRequest request)
    {
        Article article = findById(id);
        blogRepository.updateTitle(id, request.getTitle());
        return article;
    }


    public Comment commentSave(Long articleId, AddCommentRequest request) {


        Optional<Article> optionalArticle = blogRepository.findById(articleId);
        Article article = optionalArticle.get();

        //AddCommentResponse addCommentResponse = new AddCommentResponse(article,request.getBody());


        Comment comment = Comment.builder().body(request.getBody()).article(article).build();

        return commentRepository.save(comment);

    }

    public Comment findCommentById(Long articleId, Long commentId)
    {
        return commentRepository.findByIdAndArticleId(articleId,commentId);

    }

    public AllResponse findAllById(Long articleId) {
        List<Comment> commentList = commentRepository.findAllByArticleId(articleId);
        Article article = blogRepository.findById(articleId).get();

        List<CommentResponse> commentResponses = new ArrayList<>();

        for (Comment c : commentList) {
            commentResponses.add(c.toResponse());
        }

        AllResponse allResponse = new AllResponse(article,commentResponses);

        return allResponse;

    }
}
