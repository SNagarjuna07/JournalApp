package com.edigest.journalApp.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.edigest.journalApp.dto.JournalDto;
import com.edigest.journalApp.entity.User;
import com.edigest.journalApp.entity.JournalEntry;
import com.edigest.journalApp.service.JournalEntryService;
import com.edigest.journalApp.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/journal")
@Tag(name = "Journal entry APIs")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping("/all-entries")
    @Operation(summary = "Get all journal entries of a user.")
    public ResponseEntity<List<JournalDto>> getAllJournalEntriesOfUser() {

        String userName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userService.findByUserName(userName);

        List<JournalDto> journals = user.getJournalEntries()
                .stream()
                .map(e -> new JournalDto(
                        e.getTitle(),
                        e.getContent(),
                        e.getDate(),
                        e.getSentiment()
                ))
                .toList();

        return journals.isEmpty()
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(journals);
    }


    @PostMapping("/create-entry")
    @Operation(summary = "Create a journal entry of an user.")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
            String userName = authentication.getName();

            journalEntryService.saveEntry(myEntry, userName);

            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    @Operation(summary = "Get a journal entry of a user by its ID.")
    public ResponseEntity<JournalDto> getJournalEntryById(@PathVariable String myId) {
    
        ObjectId objectId;
        try {
            objectId = new ObjectId(myId);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    
        String userName = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
    
        User user = userService.findByUserName(userName);
    
        if (user.getJournalEntries().stream()
                .noneMatch(j -> j.getId().equals(objectId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    
        return journalEntryService.findById(objectId)
                .map(e -> ResponseEntity.ok(
                        new JournalDto(
                                e.getTitle(),
                                e.getContent(),
                                e.getDate(),
                                e.getSentiment()
                        )
                ))
                .orElse(ResponseEntity.notFound().build());
    }
    

    @DeleteMapping("/id/{myId}")
    @Operation(summary = "Delete a journal entry of an user by its ID.")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String myId) {

        ObjectId objectId = new ObjectId(myId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String userName = authentication.getName();

        boolean removed = journalEntryService.deleteById(objectId, userName);

        if(removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{myId}")
    @Operation(summary = "Update a journal entry of an user by its ID.")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable String myId, @RequestBody JournalEntry newEntry) {

        ObjectId objectId = new ObjectId(myId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        // Verifying whether the ID of the journal entry belongs to that particular user
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(objectId)).collect(Collectors.toList());

        // The user has an entry
        if(!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(objectId);

            if (journalEntry.isPresent()) {

                JournalEntry old = journalEntry.get();

                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle()
                    : old.getTitle());

                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent()
                    : old.getContent());

            journalEntryService.saveEntry(old);

            return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
