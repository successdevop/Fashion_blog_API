package com.productblog.controllers;

import com.productblog.dtos.LikesDto;
import com.productblog.exception.PostNotFound;
import com.productblog.exception.UserNotFound;
import com.productblog.models.Post;
import com.productblog.models.User;
import com.productblog.repositories.PostRepository;
import com.productblog.repositories.UserRepository;
import com.productblog.services.ReactionServices;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReactionController.class)
class ReactionControllerTest {

    @MockBean
    private ReactionServices reactionServices;
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;
    @Autowired
    private MockMvc mockMvc;
    private LikesDto like;

    @BeforeEach
    void setUp(){
        like = LikesDto.builder()
                .id(1L)
                .likes(1L)
                .build();
        User user = User.builder()
                .firstName("Almustapha")
                .lastName("Tukur")
                .email("atumar@gmail.com")
                .role("customer")
                .build();

        Post post = Post.builder()
                .title("test")
                .description("testing")
                .build();
        Mockito.when(reactionServices.react(like, 1L, 1L))
                .thenReturn(new ResponseEntity<>("you just liked", HttpStatus.OK));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
    }
    @Test
    void reaction() throws Exception {
        ResponseEntity<String> react = reactionServices.react(like, 1L, 1L);
        mockMvc.perform(post("/blog/api/reaction/1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}