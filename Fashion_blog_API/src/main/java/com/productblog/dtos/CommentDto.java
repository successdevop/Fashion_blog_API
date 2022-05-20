package com.productblog.dtos;

import com.productblog.models.Post;
import lombok.*;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CommentDto {
    private Long id;
    private String content;
    private LocalDateTime created_at;
    private LocalDateTime modify_at;
}
