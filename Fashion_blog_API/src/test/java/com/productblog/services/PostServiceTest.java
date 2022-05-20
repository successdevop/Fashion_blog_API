package com.productblog.services;

import com.productblog.dtos.PostDto;
import com.productblog.dtos.UserDto;
import com.productblog.exception.PostNotFound;
import com.productblog.exception.UserNotFound;
import com.productblog.models.Category;
import com.productblog.models.Post;
import com.productblog.models.User;
import com.productblog.repositories.CategoryRepository;
import com.productblog.repositories.PostRepository;
import com.productblog.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.rmi.AccessException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class PostServiceTest {

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PostService postService;
    private Post testPost;
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        modelMapper = new ModelMapper();
        List<Post> posts = new ArrayList<>();
        User testUserAdmin = User.builder()
                .id(1L)
                .firstName("Almustapha")
                .lastName("Tukur umar")
                .email("atumar4031@gmail.com")
                .role("admin")
                .created_at(LocalDateTime.now())
                .modify_at(LocalDateTime.now())
                .build();

        User testUserCustomer = User.builder()
                .id(2L)
                .firstName("Almustapha")
                .lastName("Tukur umar")
                .email("atumar4031@gmail.com")
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
        testPost = Post.builder()
                .title("test post")
                .description("test post description")
                .category(testcategory)
                .created_at(LocalDateTime.now())
                .modify_at(LocalDateTime.now())
                .build();

        posts.add(testPost);
        Mockito.when(postRepository.save(testPost)).thenReturn(testPost);
        Mockito.when(postRepository.findById(1L)).thenReturn(Optional.of(testPost));
        Mockito.when(postRepository.findAll()).thenReturn(posts);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(testUserAdmin));
        Mockito.when(userRepository.findById(2L)).thenReturn(Optional.of(testUserCustomer));
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(testcategory));
    }

    @Test
    @DisplayName("TEST: create post")
    public void shouldTest_ifPostIsCreated() throws AccessException {
        PostDto postDto = modelMapper.map(testPost, PostDto.class);
        ResponseEntity<String> responseEntity = postService.createPost(1L, 1L, postDto);
        assertEquals("post created", responseEntity.getBody());
    }
    @Test
    @DisplayName("TEST: Only admin can post")
    public void shouldTest_isOnlyAdminCanPost(){
            PostDto postDto = modelMapper.map(testPost, PostDto.class);
        assertThrows( UserNotFound.class,
                () -> postService.createPost(2L, 1L, postDto),
                "You are not allowed to perform this operation");
    }

    @Test
    @DisplayName("TEST: update post")
    public void shouldTest_ifPostIsUpdated() throws AccessException {
        PostDto postDto = modelMapper.map(testPost, PostDto.class);
        ResponseEntity<String> responseEntity = postService.updatePost(1L, postDto);
        assertEquals("post updated", responseEntity.getBody());
    }

    @Test
    @DisplayName("TEST: update only available post")
    public void shouldTest_ifOnlyAvailablePostIsUpdated() throws AccessException {
        PostDto postDto = modelMapper.map(testPost, PostDto.class);
        assertThrows(PostNotFound.class, () ->  postService.updatePost(3L, postDto), "Post not found");
    }

    @Test
    @DisplayName("TEST: post not found exception")
    public void shouldTest_ifPostNotFountIsThrown() throws AccessException {
        assertThrows(PostNotFound.class, ()-> postService.findPost(2L), "post not found");
    }

    @Test
    @DisplayName("TEST: fetch post by id")
    public void shouldFindPost_byId(){
        ResponseEntity<PostDto> responseEntity = postService.findPost(1L);
        assertEquals("test post",Objects.requireNonNull(responseEntity.getBody()).getTitle());
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("TEST: fetch all post")
    public void shouldReturnTheListOfAll_posts(){
        ResponseEntity<List<PostDto>> responseEntity = postService.findAllPosts();
        assert(Objects.requireNonNull(responseEntity.getBody()).size() > 0);
        assertEquals(HttpStatus.ACCEPTED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("TEST: delete only available post")
    public void shouldTest_ifOnlyAvailablePostIsDeleted() throws AccessException {
        PostDto postDto = modelMapper.map(testPost, PostDto.class);
        assertThrows(PostNotFound.class, () ->  postService.deletePost(3L), "Post not found");
    }

    @Test
    @DisplayName("TEST: delete post")
    public void shouldTest_ifPostIsDeleted() {
        ResponseEntity<String> responseEntity = postService.deletePost(1L);
        assertEquals("post deleted", responseEntity.getBody());
    }
}