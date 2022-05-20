package com.productblog.repositories;

import com.productblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReopitory extends JpaRepository<Comment, Long> {
}
