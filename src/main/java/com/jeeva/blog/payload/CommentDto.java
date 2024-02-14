package com.jeeva.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private long id;

    // name should not be null or empty
    @NotEmpty(message = "Name should not be Empty or Null")
    private String name;

    // email should not be Empty
    // email field validation
    @NotEmpty(message = "Email should not be Empty or Null")
    @Email
    private String email;

    // Comment Body should not be Empty
    // Comment body must be minimum 10 characters
    @NotEmpty(message = "Comment should not be Empty")
    @Size(min = 10, message = "CommentBody must be minimum 10 characters")
    private String body;
}
