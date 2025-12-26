package com.edigest.journalApp.dto;

import java.time.LocalDateTime;

import com.edigest.journalApp.enums.Sentiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalDto {

    private String title;
    private String content;
    private LocalDateTime date;
    private Sentiment sentiment;
}
