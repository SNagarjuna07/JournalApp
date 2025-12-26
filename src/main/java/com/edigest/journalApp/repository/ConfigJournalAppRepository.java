package com.edigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journalApp.entity.ConfigJournalAppEntity;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
    
}
