package com.estsoft.blogjpa;

import com.estsoft.blogjpa.model.Article;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BlogJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogJpaApplication.class, args);

        Article.builder().title("블로그 제목").content("내용");
    }

}
