package com.hakansander.sozlukscrapper.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class Topic {
    private String topicUrl;
    private String topicTitle;
    private String commentTotal;
}
