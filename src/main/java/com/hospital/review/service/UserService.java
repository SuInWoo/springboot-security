package com.hospital.review.service;

import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinReq;
import com.hospital.review.domain.entity.User;
import com.hospital.review.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public UserDto join(UserJoinReq userJoinReq) {
        // 로직 - 회원 가입

        // 회원 userName(id) 중복 check
        // 중복이면 회원가입 x --> Exception(예외)발생
        // 있으면 에러 처리
        userRepo.findByUserName(userJoinReq.getUserName())
                .ifPresent(user -> new RuntimeException("해당 UserName 중복"));

        // 회원가입 .save()
        User savedUser = userRepo.save(userJoinReq.toEntity());

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmailAddress())
                .build();
    }
}
