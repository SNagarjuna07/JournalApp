package com.edigest.journalApp.scheduler;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.edigest.journalApp.cache.AppCache;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.enums.Sentiment;
import com.edigest.journalApp.model.SentimentData;
import com.edigest.journalApp.repository.UserRepositoryImpl;
import com.edigest.journalApp.service.EmailService;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private AppCache appCache;

    // @Autowired
    // private KafkaTemplate<String, SentimentData> kafkaTemplate;
    
    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUsersAndSendSAEmail() {

        List<User> users = userRepositoryImpl.getUserforSA();
        
        for(User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
        
            List<Sentiment> sentiments = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus
            (7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            
            Map<Sentiment, Integer> sentimentCounts = new HashMap<>();

            for(Sentiment sentiment : sentiments) {
                
                if(sentiment != null) 
                    sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
            }
                
            Sentiment mostFrequentSentiment = null;

            int maxCount = 0;

            for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()) {
                    
                if(entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    
                    mostFrequentSentiment = entry.getKey();
                }
                
            }
            if(mostFrequentSentiment != null) {

                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days from Kafka" + mostFrequentSentiment).build();

               // kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);

                emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
            }
        }
    }

    // If API changes in future, every 10 mins it refreshes automatically. 
    @Scheduled(cron = "0 0/2 * ? * *")
    public void clearAppCache() {
        appCache.init();
    }
}
