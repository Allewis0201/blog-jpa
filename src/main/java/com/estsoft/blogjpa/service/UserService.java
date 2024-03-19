package com.estsoft.blogjpa.service;

import com.estsoft.blogjpa.domain.User;
import com.estsoft.blogjpa.dto.AddUserRequest;
import com.estsoft.blogjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor : final로 선언된 필드의 생성자를 자동 생성함
public class UserService {
    // controller "Post /user " -> service() 회원가입 비즈니스 로직
    // save
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User save(AddUserRequest request) {
        return userRepository.save(new User(request.getEmail(),encoder.encode(request.getPassword())));
    }
}