package com.edigest.journalApp.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.edigest.journalApp.entity.JournalEntry;

public interface JournalEntryReopsitory extends MongoRepository<JournalEntry, ObjectId> {
}

/*
 * Best practice
 * controller ---> service ---> repository
 */