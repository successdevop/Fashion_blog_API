package com.productblog.services.impl;

import com.productblog.dtos.CommentDto;
import com.productblog.exception.CommentNotFound;
import com.productblog.exception.PostNotFound;
import com.productblog.models.Comment;
import com.productblog.models.Post;
import com.productblog.models.User;
import com.productblog.repositories.CommentReopitory;
import com.productblog.repositories.PostRepository;
import com.productblog.repositories.UserRepository;
import com.productblog.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.AccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentReopitory commentReopitory;
    private final PostRepository postSRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentServiceImpl(CommentReopitory commentReopitory, PostRepository postSRepository, UserRepository userRepository) {
        this.commentReopitory = commentReopitory;
        this.postSRepository = postSRepository;
        this.userRepository = userRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public ResponseEntity<String> addComment(CommentDto commentDto, long postId, long userId) throws AccessException {
        Optional.ofNullable(commentDto.getContent())
                .orElseThrow(() -> new IllegalArgumentException("Comment is required"));

        Post selectedPost = postSRepository.findById(postId)
                .orElseThrow(()-> new PostNotFound("Post is not available now"));

        User selectedUser = userRepository.findById(userId)
                .orElseThrow(()-> new PostNotFound("Post is not available now"));
        if(!selectedUser.getRole().equals("customer"))
            throw new AccessException("You are not allowed to perform this operation");

        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setCreated_at(LocalDateTime.now());
        comment.setModify_at(LocalDateTime.now());
        
        commentReopitory.save(comment);
        return new ResponseEntity<>("You have added comment", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> updateComment(long id, CommentDto commentDto) {
        Comment comment = commentReopitory.findById(id).orElseThrow(
                ()->new PostNotFound("Post not found"));

        comment.setContent(commentDto.getContent());
        comment.setModify_at(LocalDateTime.now());
        commentReopitory.save(comment);
        return new ResponseEntity<>("You have added comment", HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<String> deleteComment(long id) {
        commentReopitory.deleteById(id);
       return new ResponseEntity<>(
                "Comment deleted",
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<CommentDto> fetchComment(long id) {
            Comment comment = commentReopitory.findById(id)
                    .orElseThrow(() -> new CommentNotFound("comment not found"));
            CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

        return new ResponseEntity<>(commentDto, HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<List<CommentDto>> fetchComments() {
        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = commentReopitory.findAll();
        for (Comment comment :comments)
            commentDtos.add(modelMapper.map(comment, CommentDto.class));
        return new ResponseEntity<>(commentDtos, HttpStatus.ACCEPTED);
    }
}
