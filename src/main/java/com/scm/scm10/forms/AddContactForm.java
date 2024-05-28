package com.scm.scm10.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddContactForm {


    @NotBlank(message = "Name not be empty")
    private String name;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email not be null")
    private String email;
    @NotBlank(message = "Phone number not be empty")
    @Size(min = 8,max = 12,message = "Phone number only contain 8 to 12 digit ")
    private String phoneNumber;

    
    private String address;
    
    private MultipartFile picture;
    @Size(max = 10000,message = "description is too long")
    private String description;
    
    private String websiteLink;
    private String linkedInLink;
    private boolean favorite=false;
}
