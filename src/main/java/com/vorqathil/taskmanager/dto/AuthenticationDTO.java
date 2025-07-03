package com.vorqathil.taskmanager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationDTO {
    @NotEmpty
    @Size(min = 2, max = 32, message = "Username should be between 2 and 32 characters")
    private String username;

    @NotEmpty
    @Size(min = 6, max = 64, message = "Password should be between 6 and 64 characters")
    private String password;
}
