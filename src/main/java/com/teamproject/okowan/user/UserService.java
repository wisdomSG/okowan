package com.teamproject.okowan.user;

import com.teamproject.okowan.aop.ApiResponseDto;

public interface UserService {

    ApiResponseDto signup(UserRequestDto userRequestDto);

    User login(UserRequestDto userRequestDto);

    ApiResponseDto logout();

    ProfileResponseDto getProfile(String username);

    ApiResponseDto updateProfile(ProfileRequestDto profileRequestDto, User user);

    User findUserByUsername(String username);

    User findUserById(Long userId);
}
