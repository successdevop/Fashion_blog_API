package com.productblog.dtos;


import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime modify_at;

    public CategoryDto(String name) {
        this.name = name;
    }
}
