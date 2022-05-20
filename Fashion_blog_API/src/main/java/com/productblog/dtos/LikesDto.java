package com.productblog.dtos;

import lombok.*;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class LikesDto {
    private Long id;
    private Long likes;
}
