package com.edigest.journalApp.entity;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
@Data // lombok for getters, setters,toString(), equals(), constructors and hashCode()
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @NonNull
    private String userName;

    private String city;

    private String email;

    private boolean sentimentAnalysis;

    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;
}
