package com.hakansander.sozlukscrapper.scrapper;

import com.hakansander.sozlukscrapper.model.Topic;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class WebScrapper {

    private List<Topic> topicList;

    private static final String URL = "https://eksisozluk.com/basliklar/gundem?p=";
    private static final String URL_END = "&&_=1609016483649";
    private static final String SCRAPPED_CLASS_NAME = "topic-list";
    private static final int TOTAL_PAGE_INDEX = 5;

    @PostConstruct
    private void runScrap() {
        log.info("[runScrap] Method is called on application start");
        scrap();
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    @Scheduled(cron = "${scheduler.cron.sozlukScrappingTriggerTime}")
    private void scrap() {
        log.info("[scrap] Method is called for scrapping the topics");

        List<Topic> finalTopicList = new ArrayList<>();

        for(int pageIndex = 1; pageIndex <= TOTAL_PAGE_INDEX; pageIndex++) {

            Document doc = null;
            try {
                doc = Jsoup.connect(URL + pageIndex + URL_END)
                        .get();
            } catch (UnknownHostException e) {
                log.error("[scrap] Could not connect to the given url");
            } catch (IOException e) {
                log.error("[scrap] IOException has been occurred while trying to connect to the given url :: ", e);
                return;
            } catch (Exception e) {
                log.error("[scrap] An exception occurred :: ", e);
                return;
            }

            List<Node> tempTopicList = null;
            if(doc != null &&
                    doc.getElementsByClass(SCRAPPED_CLASS_NAME) != null &&
                    !doc.getElementsByClass(SCRAPPED_CLASS_NAME).isEmpty() &&
                    doc.getElementsByClass(SCRAPPED_CLASS_NAME).get(1) != null) {

                tempTopicList = doc.getElementsByClass(SCRAPPED_CLASS_NAME).get(1).childNodes();

            }

            if(tempTopicList != null) {
                List<Topic> cleanedTopicList = tempTopicList.stream().filter(topic ->
                        topic != null &&
                                topic.childNodes().size() >= 2 &&
                                topic.childNodes().get(1).childNodes().size() >= 2 &&
                                !topic.childNodes().get(1).childNodes().get(1).childNodes().isEmpty() &&
                                topic instanceof Element)
                        .map(topic -> new Topic(
                                topic.childNodes().get(1).attributes().get("href"),
                                topic.childNodes().get(1).childNodes().get(0).toString(),
                                Integer.parseInt(topic.childNodes().get(1).childNodes().get(1).childNode(0).toString())))
                        .collect(Collectors.toList());


                finalTopicList.addAll(cleanedTopicList);
            }

            try {
                final int MIN = 1;
                final int MAX = 5;
                int secondsToSleep = ThreadLocalRandom.current().nextInt(MIN, MAX + 1);
                TimeUnit.SECONDS.sleep(secondsToSleep);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        topicList = finalTopicList.isEmpty() ? topicList : new ArrayList<>(finalTopicList.stream()
                        .sorted(Comparator.comparing(Topic::getCommentTotal).reversed())
                        .collect(Collectors.toList())
        );

        finalTopicList.clear();

        log.info("[scrap] The topics are retrieved :: finalTopicList={}, topicList={}", finalTopicList, topicList);
    }
}
