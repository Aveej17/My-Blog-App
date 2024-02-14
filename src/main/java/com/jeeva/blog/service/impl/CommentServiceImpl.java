package com.jeeva.blog.service.impl;

import com.jeeva.blog.exception.BlogApiException;
import com.jeeva.blog.exception.ResourceNotFoundException;
import com.jeeva.blog.model.Comment;
import com.jeeva.blog.model.Post;
import com.jeeva.blog.payload.CommentDto;
import com.jeeva.blog.repository.CommentRepository;
import com.jeeva.blog.repository.PostRepository;
import com.jeeva.blog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ModelMapper modelMapper;

    // Dto to Entity
    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
//        Comment comment = Comment.builder().
//                id(commentDto.getId()).
//                email(commentDto.getEmail()).
//                body(commentDto.getBody()).
//                name(commentDto.getName()).
//                build();
        return comment;
    }

    // Entity to Dto
    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
//        CommentDto commentDto = CommentDto.builder().
//                id(comment.getId())
//                .email(comment.getEmail())
//                .body(comment.getBody())
//                .name(comment.getName())
//                .build();
        return commentDto;
    }

    private Post findPostById(long postId){
        return postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "id", postId));
    }

    private Comment findCommentById(long commentId){
        return commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "id", commentId));
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);

        // retrieve post entity by id
        Post post = findPostById(postId);

        // set Post to comment Entity
        comment.setPost(post);

        // save comment in DB
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        // retrieve comments by postId
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        // retrieve post entity by id
        Post post = findPostById(postId);

        // retrieve comment by id
        Comment comment = findCommentById(commentId);

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to Post");
        }
        return mapToDto(comment);
    }

    @Override

    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {

        // retrieve post entity by id
        Post post = findPostById(postId);

        // retrieve comment by id
        Comment comment = findCommentById(commentId);

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to Post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updateComment = commentRepository.save(comment);
        return mapToDto(updateComment);
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
        // retrieve post entity by id
        Post post = findPostById(postId);

        // retrieve comment by id
        Comment comment = findCommentById(commentId);

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belongs to Post");
        }
        commentRepository.delete(comment);
    }


}
