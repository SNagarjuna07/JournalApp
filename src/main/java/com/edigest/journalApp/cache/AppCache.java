package com.edigest.journalApp.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edigest.journalApp.entity.ConfigJournalAppEntity;
import com.edigest.journalApp.repository.ConfigJournalAppRepository;

import jakarta.annotation.PostConstruct;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> AppCache = new HashMap<>();

    @PostConstruct
    public void init() {
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all) {
            AppCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }
}
