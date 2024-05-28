package com.scm.scm10.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.scm10.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    Page<User> findAll(Pageable pageable);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email,String password);

}
