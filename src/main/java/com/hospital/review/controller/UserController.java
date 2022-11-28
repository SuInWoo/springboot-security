package com.hospital.review.controller;

import com.hospital.review.domain.Response;
import com.hospital.review.domain.dto.UserDto;
import com.hospital.review.domain.dto.UserJoinReq;
import com.hospital.review.domain.dto.UserJoinRes;
import com.hospital.review.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinRes> join(@RequestBody UserJoinReq userJoinReq) {
        UserDto userDto = userService.join(userJoinReq);
        return Response.success(new UserJoinRes(userDto.getUserName(), userDto.getEmail()));
    }
}
