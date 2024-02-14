package com.jeeva.blog.repository;

import com.jeeva.blog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment>findByPostId(long postId);
}
