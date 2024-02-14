package com.jeeva.blog.service;

import com.jeeva.blog.payload.LoginDto;
import com.jeeva.blog.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
