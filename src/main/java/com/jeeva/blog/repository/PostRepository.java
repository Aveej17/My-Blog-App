package com.jeeva.blog.repository;

import com.jeeva.blog.model.Post;
import com.jeeva.blog.payload.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategoryId(long id);
}
