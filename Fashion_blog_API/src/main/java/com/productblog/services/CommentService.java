package com.productblog.services;

import com.productblog.dtos.CommentDto;
import org.springframework.expression.AccessException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    ResponseEntity<String> addComment(CommentDto commentDto, long postId, long userId) throws AccessException;
    ResponseEntity<String> updateComment(long id, CommentDto commentDto);
    ResponseEntity<String> deleteComment(long id);
    ResponseEntity<CommentDto> fetchComment(long id);
    ResponseEntity<List<CommentDto>> fetchComments();

}
