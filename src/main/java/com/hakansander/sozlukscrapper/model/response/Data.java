package com.hakansander.sozlukscrapper.model.response;

import com.hakansander.sozlukscrapper.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Data {
    private List<Topic> topicList;
}
