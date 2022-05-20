package com.productblog.controllers;

import com.productblog.dtos.UserDto;
import com.productblog.exception.UserAlreadyExist;
import com.productblog.models.User;
import com.productblog.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private User user;

    @MockBean
    private UserService userService;

    private UserDto inputUserDto;
    @BeforeEach
    void setUp() {
        inputUserDto = UserDto.builder()
                .firstName("Almustapha")
                .lastName("Tukur")
                .email("atumar@gmail.com")
                .role("customer")
                .build();
        UserDto outputUserDto = UserDto.builder()
                .id(1L)
                .firstName("Almustapha")
                .lastName("Tukur")
                .email("atumar@gmail.com")
                .role("customer")
                .build();

        Mockito.when(userService.createUser(inputUserDto)).thenReturn(ResponseEntity.ok(outputUserDto));
        Mockito.when(userService.fetchUserById(1L)).thenReturn(new ResponseEntity<>(inputUserDto, HttpStatus.OK));


    }

    @Test
    void createUser() throws Exception {
        ResponseEntity<UserDto> responseEntity =
                userService.createUser(inputUserDto);
        mockMvc.perform(post("/blog/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n"+
                        "\t\"firstName\": \"Almustapha\",\n" +
                        "\t\"lastName\": \"Tukur\",\n" +
                        "\t\"email\": \"atumar@gmail.com\",\n" +
                        "\t\"role\": \"customer\" \n" +
                        " }")).andExpect(status().isOk());
    }

    @Test
    void findUserById() throws Exception {
        ResponseEntity<UserDto> userDtoResponseEntity = userService.fetchUserById(1L);
        mockMvc.perform(get("/blog/api/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(inputUserDto.getFirstName()));
    }

    @Test
    void findAllUsers() throws Exception {
        ResponseEntity<UserDto> userDtoResponseEntity = userService.fetchUserById(1L);
        mockMvc.perform(get("/blog/api/user/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}