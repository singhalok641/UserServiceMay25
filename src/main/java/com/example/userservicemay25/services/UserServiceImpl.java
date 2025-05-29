package com.example.userservicemay25.services;

import com.example.userservicemay25.models.Token;
import com.example.userservicemay25.models.User;
import com.example.userservicemay25.repositories.TokenRepository;
import com.example.userservicemay25.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User authenticateUser(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return user;
    }

    @Override
    public User signUp(String name, String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isPresent()) {
            return userOptional.get();
        }

        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // JWT validation is now handled by Spring Security automatically
    // No need for manual token validation
}
