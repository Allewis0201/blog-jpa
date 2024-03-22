package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.domain.Article;
import com.estsoft.blogjpa.dto.AddArticleRequest;
import com.estsoft.blogjpa.dto.ArticleResponse;
import com.estsoft.blogjpa.dto.UpdateArticleRequest;
import com.estsoft.blogjpa.repository.BlogRepository;
import com.estsoft.blogjpa.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext ac;

    @Autowired
    private BlogRepository blogRepository;

    @Mock   // 실제 동작이 되지는 않지만 실제 동작이 된 것처럼 처리
    UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void mockMvcSetUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ac).build();
        blogRepository.deleteAll();
    }



    @Test
    public void addArticle() throws Exception {
        // given : 저장하고 싶은 블로그 정보
        AddArticleRequest request = new AddArticleRequest("제목","내용");
        // object -> json (오브젝트 매퍼 사용)
        String json = objectMapper.writeValueAsString(request);


        // when : POST /api/articles

        ResultActions resultActions = mockMvc.perform(post("/api/articles")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        );


        // then :  HttpStatusCode 201로 들어왔는지 확인
        resultActions.andExpect(status().isCreated());

        //저장이 잘 되었는지 확인
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()))
                ;
    }

    @Test
    public void showArticle() throws Exception {
        // given : blogRepository.save
        List<Article> articleList = new ArrayList<>();

        Article article1 = new Article("title1","content1");
        Article article2 = new Article("title2","content2");
        articleList.add(article1);
        articleList.add(article2);

        blogRepository.saveAll(articleList);

        // when : GET /api/articles

        ResultActions resultActions = mockMvc.perform(get("/api/articles"));


        // then : 호출 결과(Json)와 save한 데이터와 비교 [ {"title" : "title1", "content" : "content1"}, {"title" : "title2", "content" : "content2"}]
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(articleList.get(0).getTitle()))
                .andExpect(jsonPath("$[0].content").value(articleList.get(0).getContent()))
                .andExpect(jsonPath("$[1].title").value(articleList.get(1).getTitle()))
                .andExpect(jsonPath("$[1].content").value(articleList.get(1).getContent()))
                ;

    }

    @Test
    public void deleteArticle() throws Exception {
        // given : blogRepository.save

        Article article = new Article("title","content");

        blogRepository.save(article);

        Long id = article.getId();

        // when : Delete /api/articles/{id}

        ResultActions resultActions = mockMvc.perform(delete("/api/articles/{id}",id));


        // then : 삭제 결과 확인, 200 응답 코드 확인

        resultActions.andExpect(status().isOk());


        // 삭제 결과
        Optional<Article> deletedArticle = blogRepository.findById(id);
        Assertions.assertFalse(deletedArticle.isPresent());


    }

    @Test
    public void updateArticle() throws Exception {
        // given

        Article article = new Article("title","content");
        blogRepository.save(article);
        Long id = article.getId();

        UpdateArticleRequest request = new UpdateArticleRequest("제목수정","내용수정");
        // object -> json (오브젝트 매퍼 사용)
        String json = objectMapper.writeValueAsString(request);

        // when

        ResultActions resultActions = mockMvc.perform(put("/api/articles/{id}",id)
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
        );

        // then

        //수정이 잘 되었는지 확인
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("title").value(request.getTitle()))
                .andExpect(jsonPath("content").value(request.getContent()))
        ;

        Article updatedArticle = blogRepository.findById(id).orElseThrow();
        assertThat(updatedArticle.getTitle()).isEqualTo(request.getTitle());
        assertThat(updatedArticle.getContent()).isEqualTo(request.getContent());

    }

    // 코드 변경 사항 가정 (테스트 코드 삭제)

    // 코드 변경 사항 가정2 (테스트 코드 삭제)

}