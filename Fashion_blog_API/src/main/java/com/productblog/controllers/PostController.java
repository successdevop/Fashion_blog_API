package com.productblog.controllers;

import com.productblog.dtos.PostDto;
import com.productblog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.AccessException;
import java.util.List;

@RestController
@RequestMapping(value = "/blog/api/post")
public class PostController {


    private final PostService postService;
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    @PostMapping("/{categoryId}/{userId}/create")
    public ResponseEntity<String> createPost(@PathVariable("userId") long userId, @PathVariable("categoryId") long categoryId, @RequestBody PostDto postDto) throws AccessException {
        return postService.createPost(userId, categoryId, postDto);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        return  postService.updatePost(id, postDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getPosts(){
        return  postService.findAllPosts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPosts(@PathVariable("id") long id){
        return  postService.findPost(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
            postService.deletePost(id);
        return new ResponseEntity<>(
                "post deleted",
                HttpStatus.OK
        );
    }
}
