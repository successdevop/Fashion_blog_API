package com.productblog.services.impl;

import com.productblog.dtos.LikesDto;
import com.productblog.exception.PostNotFound;
import com.productblog.exception.UserNotFound;
import com.productblog.models.Like;
import com.productblog.models.Post;
import com.productblog.models.User;
import com.productblog.repositories.ReactionRepository;
import com.productblog.repositories.PostRepository;
import com.productblog.repositories.UserRepository;
import com.productblog.services.ReactionServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ReactionServicesImpl implements ReactionServices {

    private final ReactionRepository likesRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReactionServicesImpl(ReactionRepository likesRepository, UserRepository userRepository, PostRepository postRepository) {
        this.likesRepository = likesRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public ResponseEntity<String> react(LikesDto likesDto, long userId, long postId) {
        String feedback = "";
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("user not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFound("post not found"));
        Optional<Like> selectedLike = likesRepository.findByUserIdAndPostId(userId, postId);
        if (selectedLike.isEmpty()){
            Like like = modelMapper.map(likesDto, Like.class);
            like.setPost(post);
            like.setUser(user);
            like.setCreated_at(LocalDateTime.now());
            like.setModify_at(LocalDateTime.now());
            likesRepository.save(like);
            feedback = "you just liked";

        }else{
            likesRepository.deleteById(selectedLike.get().getId());
            feedback = "you just unliked";
        }
        return new ResponseEntity<>(feedback, HttpStatus.OK);

    }

}
