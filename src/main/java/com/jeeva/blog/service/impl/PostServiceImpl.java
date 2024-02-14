package com.jeeva.blog.service.impl;

import com.jeeva.blog.exception.ResourceNotFoundException;
import com.jeeva.blog.model.Category;
import com.jeeva.blog.model.Comment;
import com.jeeva.blog.model.Post;
import com.jeeva.blog.payload.PostDto;
import com.jeeva.blog.payload.PostResponse;
import com.jeeva.blog.repository.CategoryRepository;
import com.jeeva.blog.repository.CommentRepository;
import com.jeeva.blog.repository.PostRepository;
import com.jeeva.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private ModelMapper mapper;
    private PostRepository postRepository;

    private CommentRepository commentRepository;

    private CategoryRepository categoryRepository;

    // Entity to DTO
    private PostDto mapToDTO(Post post){

        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postResponse = PostDto.builder().id(post.getId()).title(post.getTitle())
//                .description(post.getDescription()).content(post.getContent()).build();
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = mapper.map(postDto, Post.class);
//        Post post = Post.builder().title(postDto.getTitle())
//                .description(postDto.getDescription()).content(postDto.getContent()).build();
        return post;
    }
    public PostServiceImpl(PostRepository postRepository,
                           ModelMapper modelMapper,
                           CommentRepository commentRepository,
                           CategoryRepository categoryRepository){
        this.postRepository = postRepository;
        this.mapper = modelMapper;
        this.commentRepository = commentRepository;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        // getting the Category From categoryDB
        Category category = categoryRepository.findById(postDto.getCategoryId()).orElseThrow(()-> new ResourceNotFoundException("Category", "id", postDto.getCategoryId()));

        // Convert DTO to Entity;
        Post post = mapToEntity(postDto);


        post.setCategory(category);
        Post newPost = postRepository.save(post);

        // Entity to DTO
        PostDto postResponse = mapToDTO(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        // sorting by Client preference
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //get content of page object
        List<Post> listOfPosts = posts.getContent();

        List<PostDto> content = listOfPosts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PostResponse postResponse = PostResponse.builder().content(content)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
        return postResponse;
    }

    @Override
    public PostDto getPostById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        return mapToDTO(post);
    }

    @Override
    public List<PostDto> getPostByCategory(long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
        List<Post> posts = postRepository.findByCategoryId(categoryId);

        return posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }


    @Override
    public PostDto updatePostById(PostDto postDto, long id) {
        // get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // getting the Category From categoryDB
        Category category = categoryRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("category", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        post.setCategory(category);

        Post updatedPost  = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        // get post by id from database
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        if (post!=null) {
            // Delete associated comments first
            List<Comment> comments = commentRepository.findByPostId(id);
            commentRepository.deleteAll(comments);

            // Then delete the post
            postRepository.deleteById(id);
        }
        postRepository.delete(post);
    }


}
