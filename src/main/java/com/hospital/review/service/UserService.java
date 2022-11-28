package com.hospital.review.service;

import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinReq;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public UserDto join(UserJoinReq request) {
        // 로직 - 회원 가입

        // 회원 userName(id) 중복 check
        // 중복이면 회원가입 x --> Exception(예외)발생
        return new UserDto("", "", "");
    }
}
