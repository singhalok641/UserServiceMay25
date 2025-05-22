package com.example.userservicemay25.dtos;

import com.example.userservicemay25.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private String email;
    private String name;
    private List<Role> roles;
}
