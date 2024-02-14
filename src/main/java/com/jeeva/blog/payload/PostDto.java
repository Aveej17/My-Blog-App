package com.jeeva.blog.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    private long id;

    // title should not be empty or null
    // title should have at least 2 characters
    @NotEmpty
    @Size(min=2, message = "Post title should have at least 2 characters")
    private String title;

    // post description should not be null or empty
    // Post description should have at least 10 characters
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    //Post content should not be null or Empty
    @NotEmpty
    private String content;
    private Set<CommentDto> comments;

    private long categoryId;
}
