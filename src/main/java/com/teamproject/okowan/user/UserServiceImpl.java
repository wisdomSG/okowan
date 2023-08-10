package com.teamproject.okowan.user;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    private final String DEFAULT_INTRODUCTION = "안녕하세요.";

    @Override
    public ApiResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        String nickname = signupRequestDto.getNickname();
        String rawPassword = signupRequestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .nickname(nickname)
                .build();
        user.setIntroduction(DEFAULT_INTRODUCTION);
        user.setAddress("");
        userRepository.save(user);

        return new ApiResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    @Override
    public ApiResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        User user = findUserByUsername(username);

        String rawPassword = loginRequestDto.getPassword();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 오류입니다.");
        }

        // Access Token 생성 및 헤더에 추가
        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);

        return new ApiResponseDto("로그인 성공", HttpStatus.OK.value());
    }

    @Override
    public ApiResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        deleteAllCookies(request, response);
        String accessToken = jwtUtil.getJwtFromHeader(request);
        redisTemplate.opsForValue().set(accessToken, "logout", Duration.ofMinutes(30));

        return new ApiResponseDto("로그아웃 성공", HttpStatus.OK.value());
    }

    @Override
    public ProfileResponseDto getProfile(Long userId) {
        User user = findUserById(userId);
        return new ProfileResponseDto(user);
    }

    @Override
    @Transactional
    public ApiResponseDto updateProfile(ProfileRequestDto profileRequestDto, Long userId, User loginUser) {
        if (!userId.equals(loginUser.getId())) {
            throw new IllegalArgumentException("자신의 프로필만 수정 가능합니다.");
        }
        loginUser.setNickname(profileRequestDto.getNickname());
        loginUser.setIntroduction(profileRequestDto.getIntroduction());
        loginUser.setAddress(profileRequestDto.getAddress());
        userRepository.save(loginUser);
        return new ApiResponseDto("프로필 수정 성공", HttpStatus.OK.value());
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 유저입니다.")
        );
    }

    @Override
    @Transactional
    public ApiResponseDto updatePassword(PasswordRequestDto passwordRequestDto, User user) {
        String currentPassword = passwordRequestDto.getCurrentPassword();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        String newPassword = passwordRequestDto.getNewPassword();
        String confirmNewPassword = passwordRequestDto.getConfirmNewPassword();
        if (!newPassword.equals(confirmNewPassword)) {
            throw new IllegalArgumentException("비밀번호 확인이 일치하지 않습니다.");
        }

        String newEncodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(newEncodedPassword);
        userRepository.save(user);
        return new ApiResponseDto("비밀번호 변경 완료", HttpStatus.OK.value());
    }

    // 모든 쿠키 삭제 메서드
    public void deleteAllCookies(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0); // 쿠키의 유효 기간을 0으로 설정하여 삭제
                cookie.setPath("/"); // 쿠키의 경로를 설정
                response.addCookie(cookie); // 응답에 쿠키를 추가하여 삭제
            }
        }
    }
}
