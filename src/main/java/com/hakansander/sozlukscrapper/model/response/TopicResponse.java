package com.hakansander.sozlukscrapper.model.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({
        "success",
        "statusCode",
        "response",
        "responseMsg",
        "data"
})
public class TopicResponse {
    private Boolean success;
    private String responseMsg;
    private int statusCode;
    private String response;
    private Data data;
}
