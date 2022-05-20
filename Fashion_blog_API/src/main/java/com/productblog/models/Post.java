package com.productblog.models;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Builder
@Entity(name="posts")
public class Post {
    @Id
    @SequenceGenerator(
            name = "post_sequence", sequenceName = "post_sequence", allocationSize = 1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "post_sequence")
    private Long id;

    private String title;
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "id")
    private Category category;

    @ManyToMany
    private List<Comment> userComment;

    @OneToMany(mappedBy = "post")
    private  Set<Like> like = new HashSet<>();

    private LocalDateTime created_at;
    private LocalDateTime modify_at;
}
