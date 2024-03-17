package com.estsoft.blogjpa.controller;


import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.model.Comment;
import com.estsoft.blogjpa.service.BlogService;
import dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {
    private final BlogService blogService;

    public CommentController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/comments/{articleId}")
    public void addComment(@PathVariable Long articleId, @RequestBody AddCommentRequest request) {
        Comment comment = blogService.commentSave(articleId, request);
    }

    @GetMapping("/comments/{articleId}/{commentId}")
    public ResponseEntity<CommentResponse> showCommentById(@PathVariable Long articleId, @PathVariable Long commentId)
    {
        Comment comment = blogService.findCommentById(articleId,commentId);
        CommentResponse commentResponse = comment.toResponse();

        return ResponseEntity.ok(commentResponse);
    }

    @GetMapping("/comments/{articleId}")
    public ResponseEntity<AllResponse> showOneAll(@PathVariable Long articleId)
    {
        AllResponse allResponse = blogService.findAllById(articleId);

        return ResponseEntity.ok(allResponse);
    }
}
