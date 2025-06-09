package com.example.userservicemay25.services;

import com.example.userservicemay25.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Optional;

public interface UserService {
    User signUp(String name, String email, String password) throws JsonProcessingException;

    User authenticateUser(String email, String password);

    User getUserByEmail(String email);
}
