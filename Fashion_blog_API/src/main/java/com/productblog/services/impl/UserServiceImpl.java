package com.productblog.services.impl;

import com.productblog.dtos.UserDto;
import com.productblog.exception.UserAlreadyExist;
import com.productblog.exception.UserNotFound;
import com.productblog.models.User;
import com.productblog.repositories.UserRepository;
import com.productblog.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public ResponseEntity<UserDto> createUser(UserDto userDto) {
        Optional<User> selected = userRepository.findByEmail(userDto.getEmail());
               if (selected.isPresent())
                   throw new UserAlreadyExist("this email"+ userDto.getEmail()+" has been taken");

        User user = modelMapper.map(userDto, User.class);
        user.setCreated_at(LocalDateTime.now());
        user.setModify_at(LocalDateTime.now());
        UserDto userDto1 = modelMapper.map(userRepository.save(user), UserDto.class);
        return new ResponseEntity<>(
                userDto1,
                HttpStatus.OK
        );
    }

    @Override
    public  ResponseEntity<UserDto> updateUser(UserDto userDto, long id) {
        User  user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFound("user not found"));
        if(!userDto.getFirstName().isEmpty() && !userDto.getFirstName().isBlank())
            user.setFirstName(userDto.getFirstName());
        if(!userDto.getLastName().isEmpty() && !userDto.getLastName().isBlank())
        user.setLastName(userDto.getLastName());
        if(!userDto.getEmail().isEmpty() && !userDto.getEmail().isBlank())
        user.setEmail(userDto.getEmail());
        user.setModify_at(LocalDateTime.now());
       UserDto userDto1 = modelMapper.map(userRepository.save(user), UserDto.class);

        return new ResponseEntity<>(
                userDto1,
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<UserDto> fetchUserById(long id) {
          User  user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFound("user not found"));
          UserDto selectedUserDto = modelMapper.map(user, UserDto.class);
       return new ResponseEntity<>(
               selectedUserDto,
               HttpStatus.ACCEPTED
       );
    }

    @Override
    public ResponseEntity<List<UserDto>> fetchUsers() {
        List<UserDto> usersDto = new ArrayList<>();
        List<User> users =  userRepository.findAll();
           for (User user: users)
               usersDto.add(modelMapper.map(user, UserDto.class));
       return new ResponseEntity<>(
               usersDto,
               HttpStatus.ACCEPTED
       );
    }

    @Override
    public ResponseEntity<UserDto> findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("user not found"));

        UserDto userDto = modelMapper.map(user, UserDto.class);
        return new ResponseEntity<>(
                userDto,
                HttpStatus.ACCEPTED
                );
    }
}
