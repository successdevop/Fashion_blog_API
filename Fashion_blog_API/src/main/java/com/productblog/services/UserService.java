package com.productblog.services;

import com.productblog.dtos.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDto> createUser(UserDto userDto);
    ResponseEntity<UserDto> updateUser(UserDto userDto, long id);
    ResponseEntity<UserDto> fetchUserById(long id);
    ResponseEntity<UserDto> findUserByEmail(String email);
    ResponseEntity<List<UserDto>> fetchUsers();
}
