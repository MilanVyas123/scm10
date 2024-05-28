package com.scm.scm10.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm10.entities.User;
import com.scm.scm10.helper.Helper;
import com.scm.scm10.repo.UserRepository;

//to do preprocess for each controller endpoint like access user info in every page
@ControllerAdvice
public class RootController {
    
    @Autowired
    UserRepository repository;

    @ModelAttribute
    public void getLogginedUserCredintials(Model model, Authentication authentication) {


        if (authentication != null) {
            System.out.println(authentication.getName());
            String email=Helper.getEmailOfLoggedInUser(authentication);
            User user=repository.findByEmail(email).orElse(null);
            model.addAttribute("loggedUser", user);

        }
    }
}
