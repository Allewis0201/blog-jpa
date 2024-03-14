package com.estsoft.blogjpa.controller;

import com.estsoft.blogjpa.external.ExampleAPIClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonParseTestController {
    private final ExampleAPIClient parser;

    public JsonParseTestController(ExampleAPIClient parser) {
        this.parser = parser;
    }

    @GetMapping("/api/test")
    public String test()
    {
        parser.parseAndSave();

        return "";
    }
}
