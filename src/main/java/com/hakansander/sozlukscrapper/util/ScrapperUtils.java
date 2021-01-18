package com.hakansander.sozlukscrapper.util;

import com.hakansander.sozlukscrapper.model.Topic;

import java.util.List;

public class ScrapperUtils {

    public static List<Topic> refactorThousand(List<Topic> scrappedList) {

        for(Topic topic : scrappedList) {

            String commentTotal = topic.getCommentTotal();

            if(commentTotal.contains("b")) {
                topic.setCommentTotal(commentTotal.substring(0,topic.getCommentTotal().lastIndexOf(",")) + "00" );
            }

        }

        return scrappedList;
    }
}
