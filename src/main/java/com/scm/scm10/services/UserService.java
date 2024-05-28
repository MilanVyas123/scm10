package com.scm.scm10.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scm.scm10.entities.User;
import com.scm.scm10.helper.ResourceNotFoundException;
import com.scm.scm10.repo.UserRepository;;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public Optional<User> findUserbyId(int userId) {
        Optional<User> user = repository.findById(userId);
        return user;
    }

    public Optional<User> findUserbyEmail(String email) {
        Optional<User> user = repository.findByEmail(email);

        return user;
    }

    public Optional<User> updateUser(User user) {
        User user2 = repository.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());

        BeanUtils.copyProperties(user, user2);

        repository.save(user2);
        return Optional.ofNullable(user2);
    }

    public void deleteUser(int id) {

        repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        repository.deleteById(id);
    }

    public boolean isUserExist(int id) {
        User user = repository.findById(id).orElse(null);

        return user != null ? true : false;

    }

    public boolean isUserExistbyEmail(String email) {
        User user = repository.findByEmail(email).orElse(null);

        return user != null ? true : false;
    }

    public User saveUser(User user) {
        return repository.save(user);
    }

    public List<User> getAllUsers() {
        List<User> users = repository.findAll();
        for (User user : users) {
            // Decode password field for each user
            user.setPassword("hello");
        }
        return users;
    }

    // @Autowired
    // UserRepository repository;
    // public List<User> getAllUsers()
    // {
    // return repository.findAll();
    // }
    // public boolean saveUser(User user) {
    // for (int i = 1; i < 100; i++) {
    // User newUser = new User(0,user.getFirst_name(),
    // user.getLast_name(),user.getCompany(),user.getPhone(), user.getWebsite()
    // ,user.getVisitors(), user.getEmail(),user.getPassword(),user.getFilename());

    // // Set other fields as needed

    // repository.save(newUser); // Save the new User object
    // }
    // return true;
    // }

    // public List<User> findAll(int pageNumber,int pageSize)
    // {
    // PageRequest pageRequest=PageRequest.of(pageNumber, pageSize);
    // List<User> list=repository.findAll(pageRequest).getContent();
    // return list;

    // }

}
