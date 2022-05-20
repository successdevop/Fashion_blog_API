package com.productblog.dtos;
import com.productblog.models.Category;
import com.productblog.models.Comment;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private Category category;
}
