package com.example.userservicemay25.services;

import com.example.userservicemay25.models.Token;
import com.example.userservicemay25.models.User;

public interface UserService {
    public User authenticateUser(String email, String password);

    public User signUp(String name, String email, String password);

    User findByEmail(String userEmail);
}
