package com.edigest.journalApp.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "config_journal_app")
@Data // lombok for getters, setters,toString(), equals(), constructors and hashCode()
@NoArgsConstructor
public class ConfigJournalAppEntity {

    private String key;
    private String value;

}
