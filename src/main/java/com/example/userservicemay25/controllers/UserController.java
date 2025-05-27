package com.example.userservicemay25.controllers;

import com.example.userservicemay25.dtos.*;
import com.example.userservicemay25.models.Token;
import com.example.userservicemay25.models.User;
import com.example.userservicemay25.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        Token token = userService.login(
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(token.getValue());
        return responseDto;
    }

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

    @GetMapping("/logout")
    public ResponseEntity<Void> logOut(@RequestBody LogoutRequestDto requestDto) {
        return null;
    }

    @GetMapping("/validate/{token}")
    public ResponseEntity<UserDto> validateToken(@PathVariable("token") String tokenValue) {
        User user = userService.validateToken(tokenValue);
        if(user == null) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.NOT_FOUND
            );
        }

        return new ResponseEntity<>(
                UserDto.from(user),
                HttpStatus.OK
        );
    }
}
