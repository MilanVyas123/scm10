package com.scm.scm10.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm.scm10.entities.Contact;
import com.scm.scm10.entities.User;

public interface ContactRepo extends JpaRepository<Contact,Integer> {
    List<Contact> findByUser(User user);
}
