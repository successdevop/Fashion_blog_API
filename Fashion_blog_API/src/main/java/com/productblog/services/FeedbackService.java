package com.productblog.services;

import com.productblog.dtos.CommentDto;
import com.productblog.dtos.LikesDto;
import com.productblog.dtos.PostDto;
import com.productblog.dtos.UserDto;

import java.util.List;

public interface FeedbackService {
    List<CommentDto>  getUserComments(UserDto userDto);
    List<CommentDto> getPostComments(PostDto postDto);
    long getPostLikes(PostDto postDto);
    long getPostDislikes(PostDto postDto);

}
