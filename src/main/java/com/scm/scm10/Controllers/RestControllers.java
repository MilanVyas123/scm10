package com.scm.scm10.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm.scm10.entities.User;
import com.scm.scm10.services.UserService;

import io.micrometer.core.ipc.http.HttpSender.Response;

@RestController
@RequestMapping("/v1/api")
public class RestControllers {

    @Autowired
    UserService userService;

    @GetMapping("/view")
    public ResponseEntity<List<User>> getUsers()
    {
        return new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
    }

}
