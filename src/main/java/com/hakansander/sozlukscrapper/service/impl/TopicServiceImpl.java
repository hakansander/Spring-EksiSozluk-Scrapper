package com.hakansander.sozlukscrapper.service.impl;

import com.hakansander.sozlukscrapper.model.Topic;
import com.hakansander.sozlukscrapper.model.response.Data;
import com.hakansander.sozlukscrapper.model.response.TopicResponse;
import com.hakansander.sozlukscrapper.scrapper.WebScrapper;
import com.hakansander.sozlukscrapper.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    @Autowired
    private WebScrapper webScrapper;

    public TopicResponse topicListHandler() {
        List<Topic> topicList = webScrapper.getTopicList();

        TopicResponse topicResponse = new TopicResponse();

        if(!topicList.isEmpty()) {
            topicResponse.setSuccess(true);
            topicResponse.setStatusCode(HttpStatus.OK.value());
            topicResponse.setResponse(HttpStatus.OK.getReasonPhrase());
            topicResponse.setResponseMsg("Successfully returned scrapped topic list");
            topicResponse.setData(new Data(topicList));
            return topicResponse;
        }

        topicResponse.setSuccess(false);
        topicResponse.setStatusCode(HttpStatus.NO_CONTENT.value());
        topicResponse.setResponse(HttpStatus.NO_CONTENT.getReasonPhrase());
        topicResponse.setResponseMsg("Scrapper is failed, no data found");
        return topicResponse;
    }
}
