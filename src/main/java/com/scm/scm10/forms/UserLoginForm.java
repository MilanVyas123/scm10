package com.scm.scm10.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginForm {

    @NotBlank(message = "Name not be empty")
    private String name;

    @NotBlank(message = "Email not be empty")
    @Email(message = "Inalid email")
    private String email;

    @Size(min = 6,message = "Password must contain at least 6 characters")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$", message = "Password must contain one upper and lower case character, one digit, one special symbol, and no whitespace.")
    private String password;
    
    @Size(min = 8,max = 12,message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message = "About not be empty")
    private String about;
}
