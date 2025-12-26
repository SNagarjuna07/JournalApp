package com.edigest.journalApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    
    @NotEmpty
    private String userName;

    @NotEmpty
    private String password;

}
