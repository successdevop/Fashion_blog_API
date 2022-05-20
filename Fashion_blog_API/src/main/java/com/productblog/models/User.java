package com.productblog.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Builder
@Entity(name="users")
public class User {
    @Id
    @SequenceGenerator(
            name ="user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;

    @OneToMany(mappedBy = "post")
    private List<Like> like;

    @OneToOne(mappedBy = "user")
    private Comment comment;

    private LocalDateTime created_at;
    private LocalDateTime modify_at;

}
