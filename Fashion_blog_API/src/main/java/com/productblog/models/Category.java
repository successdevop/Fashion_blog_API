package com.productblog.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity(name="category")

public class Category {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<Post> post = new HashSet<>();

    private LocalDateTime created_at;
    private LocalDateTime modify_at;

    public Category(String name, LocalDateTime created_at, LocalDateTime modify_at) {
        this.name = name;
        this.created_at = created_at;
        this.modify_at = modify_at;
    }
}
