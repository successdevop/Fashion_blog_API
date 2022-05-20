package com.productblog.controllers;

import com.productblog.dtos.LikesDto;
import com.productblog.services.ReactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/api/reaction")
public class ReactionController {

    private final ReactionServices likeServices;

    @Autowired
    public ReactionController(ReactionServices likeServices) {
        this.likeServices = likeServices;
    }

    @PostMapping("/{userId}/{postId}")
    public ResponseEntity<String> reaction(
            @RequestBody LikesDto likesDto,
            @PathVariable("userId") long userId,
            @PathVariable("postId") long postId) {
        return likeServices.react(likesDto, userId, postId);

    }
}
