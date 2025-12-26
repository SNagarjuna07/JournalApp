package com.edigest.journalApp.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.edigest.journalApp.enums.Sentiment;

import lombok.Data;

@Document(collection = "journal_entries")
@Data // lombok for getters, setters,toString(), equals(), constructors and hashCode()
public class JournalEntry {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;

}
