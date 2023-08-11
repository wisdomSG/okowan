package com.teamproject.okowan.user;

import com.teamproject.okowan.aop.ApiResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    /**
     * 회원가입
     *
     * @param signupRequestDto 회원가입 요청 데이터
     * @return 요청 처리 결과
     */
    ApiResponseDto signup(SignupRequestDto signupRequestDto);

    /**
     * 로그인
     *
     * @param loginRequestDto 로그인 요청 데이터
     * @param response        Http 반환(헤더 추가용)
     * @return 요청 처리 결과
     */
    ApiResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response);

    /**
     * 로그아웃
     *
     * @param request  로그아웃 요청
     * @param response 로그아웃 응답(쿠키 제거)
     * @return 요청 처리 결과
     */
    ApiResponseDto logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 프로필 조회
     *
     * @param userId 조회할 유저의 ID
     * @return 조회한 유저의 이메일, 자기소개
     */
    ProfileResponseDto getProfile(Long userId);

    /**
     * 프로필 수정
     *
     * @param profileRequestDto 프로필 수정 요청 데이터
     * @param userId            프로필 수정할 유저의 ID
     * @param user              프로필을 수정할 유저
     * @return 요청 처리 결과
     */
    ApiResponseDto updateProfile(ProfileRequestDto profileRequestDto, Long userId, User user);

    /**
     * 이메일로 유저 조회
     *
     * @param username 조회할 유저 이메일
     * @return 조회한 유저
     */
    User findUserByUsername(String username);

    /**
     * ID로 유저 조회
     *
     * @param userId 조회할 유저 ID
     * @return 조회한 유저
     */
    User findUserById(Long userId);

    /**
     * 비밀번호 변경
     *
     * @param passwordRequestDto 비밀번호 변경 요청 데이터
     * @param user               비밀번호를 변경할 유저
     * @return 요청 처리 결과
     */
    ApiResponseDto updatePassword(PasswordRequestDto passwordRequestDto, User user);
}
