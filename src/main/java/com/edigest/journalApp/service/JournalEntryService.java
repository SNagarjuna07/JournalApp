package com.edigest.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.repository.JournalEntryReopsitory;


@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryReopsitory journalEntryRepository;

    @Autowired
    private UserService userService;

    

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) {

        try {
            // Extracting username
            User user = userService.findByUserName(userName);

            journalEntry.setDate(LocalDateTime.now());

            JournalEntry saved = journalEntryRepository.save(journalEntry);

            // Adding the journal entry of a particular user
            user.getJournalEntries().add(saved);

            // Just for @Transaction. Did it deliberately
            // user.setUserName(null);

            userService.saveUser(user);

        } catch (Exception e) {
            System.out.println(e);

            throw new RuntimeException("An error occured while saving the entry.", e);
        }

    }

    // Overloading method used for updating
    public void saveEntry(JournalEntry journalEntry) {

        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {

        boolean removed = false;

        try {
            // Extract username
            User user = userService.findByUserName(userName);

            // If id matches, then deletes
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));

            if(removed) {
                userService.saveUser(user);

                journalEntryRepository.deleteById(id);
            }
        
        } catch(Exception e) {
            System.out.println(e);

            throw new RuntimeException("An error occured while deleting the entry." + e);
        }

        return removed;
    }

}
