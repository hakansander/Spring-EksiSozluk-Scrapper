package com.hakansander.sozlukscrapper.controller;

import com.hakansander.sozlukscrapper.model.response.TopicResponse;
import com.hakansander.sozlukscrapper.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/sozluk")
public class TopicController {
    @Autowired
    TopicService topicService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/topics")
    public TopicResponse topicList() {
        return topicService.topicListHandler();
    }
}
