package com.example.userservicemay25.services;

import com.example.userservicemay25.dtos.SendEmailDto;
import com.example.userservicemay25.models.User;
import com.example.userservicemay25.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private ObjectMapper objectMapper;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           KafkaTemplate<String, String> kafkaTemplate,
                           ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public User signUp(String name, String email, String password) throws JsonProcessingException {
        // Check if the user already exists
        Optional<User> userOptional = userRepository.findByEmail(email);

        // If, the user exists -> return existing User object
        if(userOptional.isPresent()) {
            return userOptional.get();
        }

        // Else, create a new user with the details and return the User object
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        // SendEmailDto to be sent to the email service
        SendEmailDto emailDto = new SendEmailDto();
        emailDto.setEmail(user.getEmail());
        emailDto.setSubject("Welcome to Scaler!");
        emailDto.setBody("Happy to have you onboard. Your journey starts today... ");

        // Push an event to kafka, which will be read by the email service
        // and then the email service will send a welcome email to the user
        // key -> topic, value -> event
        kafkaTemplate.send(
                "sendEmail",
                objectMapper.writeValueAsString(emailDto)
        );
        return userRepository.save(user);
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
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
