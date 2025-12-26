// package com.edigest.journalApp.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.kafka.annotation.KafkaListener;
// import org.springframework.stereotype.Service;

// import com.edigest.journalApp.model.SentimentData;


// @Service
// public class KafkaService {

//     @Autowired
//     private EmailService emailService;

//     @KafkaListener(topics = "weekly-sentiments", groupId = "demo-group-4")
//     // Mimics consumer
//     public void consume(SentimentData sentimentData) {
//         sendEmail(sentimentData);
//     }

//     public void sendEmail(SentimentData sentimentData) {
//         emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
//     }
// }
