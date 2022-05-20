package com.productblog.services;

import com.productblog.dtos.LikesDto;
import com.productblog.models.Category;
import com.productblog.models.Like;
import com.productblog.models.Post;
import com.productblog.models.User;
import com.productblog.repositories.PostRepository;
import com.productblog.repositories.ReactionRepository;
import com.productblog.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReactionServicesTest {
    @MockBean
    private ReactionRepository reactionRepository;

    @MockBean
    UserRepository userRepository;

    @MockBean
    PostRepository postRepository;

    @Autowired
    private ReactionServices reactionServices;
    Like likes;
    Post post;
    User user;
    @BeforeEach
    void setUp() {
        likes = Like.builder()
                .likes(1L)
                .build();

        user = User.builder()
                .id(1L)
                .firstName("test first name")
                .lastName("test last name")
                .role("customer")
                .created_at(LocalDateTime.now())
                .modify_at(LocalDateTime.now())
                .build();

        Category testcategory = Category.builder()
                .id(1L)
                .name("test category")
                .created_at(LocalDateTime.now())
                .modify_at(LocalDateTime.now())
                .build();
        post = Post.builder()
                .id(1L)
                .title("test post")
                .description("test post description")
                .category(testcategory)
                .created_at(LocalDateTime.now())
                .modify_at(LocalDateTime.now())
                .build();

        Mockito.when(reactionRepository.save(likes)).thenReturn(likes);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(post));
    }

    @Test
    @DisplayName("TEST for reaction: like")
    public void shouldTest_ifReactionIsAdded(){
        LikesDto likesDto = new ModelMapper().map(likes, LikesDto.class);
        ResponseEntity<String> responseEntity = reactionServices.react(likesDto, 1L, 1L);
        assertEquals("you just liked", responseEntity.getBody());
    }

}