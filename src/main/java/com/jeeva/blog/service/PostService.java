package com.jeeva.blog.service;

import com.jeeva.blog.payload.PostDto;
import com.jeeva.blog.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long PostId);

    PostDto updatePostById(PostDto postDto, long PostId);

    void deletePostById(long postId);

    List<PostDto>getPostByCategory(long categoryId);
}
