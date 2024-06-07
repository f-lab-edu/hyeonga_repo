package com.hyeongarl.service;

import com.hyeongarl.entity.User;
import com.hyeongarl.error.UserAlreadyExistException;
import com.hyeongarl.error.UserNotFoundException;
import com.hyeongarl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/*
    @Service
        Spring Framework에 의해 관리되는 서비스 클래스임을 명시
    @RequiredArgsConstructor
        롬복 라이브러리에서 final 필드에 대한 생성자를 자동으로 생성
 */
/**
     save()             : 신규 등록
     findById()         : 아이디로 사용자 조회
     loadUserByUsername : 사용자 이메일을 사용하여 정보 가져오기
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        // 이미 존재하는 이메일인 경우
        if(userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new UserAlreadyExistException();
        }

        return userRepository.save(User.builder()
                .userEmail(user.getUserEmail())
                .password(user.getPassword())
                .build());
    }

    // userId로 유저 검색
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User findByUserEmail(String userEmail) {
        return userRepository.findByUserEmail(userEmail)
                .orElseThrow(UserNotFoundException::new);
    }
}
