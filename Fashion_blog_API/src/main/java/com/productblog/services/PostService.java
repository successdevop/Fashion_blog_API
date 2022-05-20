package com.productblog.services;

import com.productblog.dtos.PostDto;
import org.springframework.http.ResponseEntity;

import java.rmi.AccessException;
import java.util.List;

public interface PostService {
    ResponseEntity<String>  createPost(long userId, long categoryId, PostDto postDto) throws AccessException;
    ResponseEntity<String>  updatePost(long id, PostDto postDto);
    ResponseEntity<String>  deletePost(long id);
    ResponseEntity<List<PostDto>> findAllPosts();
    ResponseEntity<PostDto> findPost(long id);
}
