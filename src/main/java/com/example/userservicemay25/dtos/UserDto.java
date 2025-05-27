package com.example.userservicemay25.dtos;

import com.example.userservicemay25.models.Role;
import com.example.userservicemay25.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserDto {
    private String email;
    private String name;
    private List<Role> roles;

    public static UserDto from(User user) {
        if(user == null) return null;

        UserDto userDto = new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setRoles(user.getRoles());
        return userDto;
    }
}
