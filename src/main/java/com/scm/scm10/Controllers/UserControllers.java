package com.scm.scm10.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm10.services.UserService;

@Controller
@RequestMapping("/user")
public class UserControllers {
    @Autowired
    UserService service;


    @GetMapping("/dashboard")
    public String getDashBoard()
    {
        return "user/dashboard";
    }

    @GetMapping("/profile")
    public String getProfile()
    {
        return "user/profile";
    }
}
