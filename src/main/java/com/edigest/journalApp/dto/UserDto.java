package com.edigest.journalApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotEmpty
    private String userName;

    private String city;

    private String email;

    private boolean sentimentAnalysis;
    
    @NotEmpty
    private String password;

}
