package com.example.userservicemay25.controllers;

import com.example.userservicemay25.dtos.*;
import com.example.userservicemay25.models.Token;
import com.example.userservicemay25.models.User;
import com.example.userservicemay25.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

//    @PostMapping("/login")
//    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
//    }

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignUpRequestDto requestDto) {
        User user = userService.signUp(
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        // Converting from user to UserDto
        return UserDto.from(user);
    }

    // All the authentication is now handled by JWT/oAUth
    // Clients must use OAuth flow now to get the JWT token

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile(JwtAuthenticationToken token) {
        String userEmail = token.getName();
        User user = userService.getUserByEmail(userEmail);

        return ResponseEntity.ok(UserDto.from(user));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<String> adminEndpoint(JwtAuthenticationToken token) {
        return ResponseEntity.ok("Hello " + token.getName() + "! You are accessing a admin endpoint");
    }
}
