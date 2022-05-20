package com.productblog.services.impl;

import com.productblog.dtos.PostDto;
import com.productblog.exception.CategoryNotFound;
import com.productblog.exception.PostNotFound;
import com.productblog.exception.UserNotFound;
import com.productblog.models.Category;
import com.productblog.models.Post;
import com.productblog.models.User;
import com.productblog.repositories.CategoryRepository;
import com.productblog.repositories.PostRepository;
import com.productblog.repositories.UserRepository;
import com.productblog.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private  final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public ResponseEntity<String> createPost(long userId, long categoryId, PostDto postDto){

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFound("category not found"));
        User selectedUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("user not found"));

        if(!selectedUser.getRole().equalsIgnoreCase("admin"))
            throw new UserNotFound("You are not allowed to perform this operation");
        Post post = modelMapper.map(postDto, Post.class);
        post.setCategory(category);
        post.setCreated_at(LocalDateTime.now());
        post.setModify_at(LocalDateTime.now());
       postRepository.save(post);
        return new ResponseEntity<>("post created", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> updatePost(long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new PostNotFound("post not found"));

        if ( !postDto.getTitle().isEmpty() && !postDto.getTitle().isBlank()
                && !postDto.getDescription().isEmpty() && !postDto.getDescription().isBlank()){
            post.setTitle(postDto.getTitle());
            post.setDescription(postDto.getDescription());
            post.setModify_at(LocalDateTime.now());
            postRepository.save(post);
        }
        return new ResponseEntity<>("post updated", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<PostDto>> findAllPosts() {
        List<PostDto> postDtos = new ArrayList<>();
        List<Post> posts =  postRepository.findAll();
        for (Post post: posts)
            postDtos.add(modelMapper.map(post, PostDto.class));
        return new ResponseEntity<>(postDtos, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<PostDto> findPost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFound("post not found"));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        return new ResponseEntity<>(postDto, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> deletePost(long id) {
        Post post = postRepository.findById(id).orElseThrow(()-> new PostNotFound("post not found"));
        postRepository.delete(post);
        return new ResponseEntity<>("post deleted", HttpStatus.ACCEPTED);
    }

}
