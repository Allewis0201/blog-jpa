package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.external.ExampleAPIClient;
import com.estsoft.blogjpa.model.Article;
import com.estsoft.blogjpa.repository.BlogRepository;
import dto.AddArticleRequest;
import dto.UpdateArticleRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {
    private final BlogRepository blogRepository;


    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;

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



}
