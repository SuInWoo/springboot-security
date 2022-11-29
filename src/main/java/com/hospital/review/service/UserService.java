package com.hospital.review.service;

import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinReq;
import com.hospital.review.domain.entity.User;
import com.hospital.review.exception.ErrorCode;
import com.hospital.review.exception.HospitalReviewAppException;
import com.hospital.review.repository.UserRepo;
import com.hospital.review.utils.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.token.secret}")
    private String secretKey;

    public UserDto join(UserJoinReq userJoinReq) {
        // 로직 - 회원 가입

        // 회원 userName(id) 중복 check
        // 중복이면 회원가입 x --> Exception(예외)발생
        // 있으면 에러 처리
        userRepo.findByUserName(userJoinReq.getUserName())
                .ifPresent(user -> {
                    throw new HospitalReviewAppException(ErrorCode.DUPLICATED_USER_NAME,
                            String.format("UserName: %s", userJoinReq.getUserName()));
                });

        // 회원가입 .save()
        User savedUser = userRepo.save(userJoinReq.toEntity(encoder.encode(userJoinReq.getPassword())));

        return UserDto.builder()
                .id(savedUser.getId())
                .userName(savedUser.getUserName())
                .email(savedUser.getEmailAddress())
                .build();
    }

    public String login(String userName, String password) {

        //userName이 있는지 확인
        User user = userRepo.findByUserName(userName).orElseThrow(
                () -> new HospitalReviewAppException(ErrorCode.NOT_FOUND,
                        String.format("%s는 가입되지 않은 userName 입니다.", userName)));

        // password 일치 여부 확인
        if (!encoder.matches(password, user.getPassword())) {
            throw new HospitalReviewAppException(ErrorCode.INVALID_PASSWORD, "password가 일치하지 않습니다.");
        }

        // 두 가지 확인이 pass면 Token 발행
        return JwtTokenUtil.createToken(userName, secretKey, 1000 * 60 * 60);
    }
}
