package com.pwr.project.controllers;

import com.pwr.project.entities.User;
import com.pwr.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sync-user")
    public ResponseEntity<User> syncUser(@RequestBody User user) {
        log.info("Received sync-user request for email: {}", user.getEmail());
        try {
            log.debug("User details: firstName={}, surname={}, isSeller={}",
                user.getFirstName(), user.getSurname(), user.getIsSeller());

            User savedUser = userRepository.save(user);
            log.info("Successfully saved user with ID: {}", savedUser.getId());

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            log.error("Error syncing user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}