package com.estsoft.blogjpa.external;

import com.estsoft.blogjpa.service.BlogService;
import com.estsoft.blogjpa.dto.AddArticleRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * RestTemplate : HTTP 통신을 위한 도구
 * 외부 도메인으로부터 데이터를 가져오거나 전송할 때 사용
 */

@Slf4j
@Component
public class ExampleAPIClient {
    // 외부 API 호출 -> json 받아오기
//    private final RestTemplate restTemplate;

    private final BlogService blogService;

    public ExampleAPIClient(BlogService blogService) {
        this.blogService = blogService;
    }

    //
//    public ExampleAPIClient(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
    public void parseAndSave()
    {
        RestTemplate restTemplate = new RestTemplate(); // RestTemplate 객체 생성
        String url = "https://jsonplaceholder.typicode.com/posts";

        ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
        if(response.getStatusCode().is2xxSuccessful())
        {
            log.info("body: {}",response.getBody());
            //List<LinkedHashMap<String,Object>> 형태로 넘어오는 것을 확인
            List<LinkedHashMap<String,Object>> list = response.getBody();


            //title, body
            for(LinkedHashMap<String,Object> map : list)
            {
                String title = (String) map.get("title");
                String content = (String) map.get("body");

                blogService.save(new AddArticleRequest(title,content));

            }
        }
    }
}
