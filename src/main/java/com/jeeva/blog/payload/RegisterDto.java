package com.jeeva.blog.payload;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.PrimitiveIterator;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class RegisterDto {
    private String name;
    private String username;
    private String email;
    @Size(min=8, message = "Password should have at least 8 characters")
    private String password;
}
