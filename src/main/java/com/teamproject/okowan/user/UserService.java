package com.teamproject.okowan.user;

import com.teamproject.okowan.aop.ApiResponseDto;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * 회원가입
     *
     * @param userRequestDto 회원가입 요청 데이터
     * @return 요청 처리 결과
     */
    ApiResponseDto signup(UserRequestDto userRequestDto);

    /**
     * 로그인
     *
     * @param userRequestDto 로그인 요청 데이터
     * @param response       Http 반환(헤더 추가용)
     * @return 요청 처리 결과
     */
    ApiResponseDto login(UserRequestDto userRequestDto, HttpServletResponse response);

    /**
     * 로그아웃
     *
     * @return 요청 처리 결과
     */
    ApiResponseDto logout();

    /**
     * 프로필 조회
     *
     * @param username 조회할 유저의 이메일
     * @return 조회한 유저의 이메일, 자기소개
     */
    ProfileResponseDto getProfile(String username);

    /**
     * 프로필 수정
     *
     * @param profileRequestDto 프로필 수정 요청 데이터
     * @param user              프로필을 수정할 유저
     * @return 요청 처리 결과
     */
    ApiResponseDto updateProfile(ProfileRequestDto profileRequestDto, User user);

    /**
     * 이메일로 유저 조회
     *
     * @param username 조회할 유저 이메일
     * @return 조회한 유저
     */
    User findUserByUsername(String username);
}
