package com.productblog.services;

import com.productblog.dtos.LikesDto;
import org.springframework.http.ResponseEntity;

public interface ReactionServices {
    ResponseEntity<String> react(LikesDto likesDto, long userId, long postId);
}
