package com.teamproject.okowan.user;

import com.teamproject.okowan.aop.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final String DEFAULT_INTRODUCTION = "안녕하세요.";

    @Override
    public ApiResponseDto signup(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();

        if(userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }

        String rawPassword = userRequestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .introduction(DEFAULT_INTRODUCTION)
                .build();
        userRepository.save(user);

        return new ApiResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    @Override
    public User login(UserRequestDto userRequestDto) {
        String username = userRequestDto.getUsername();
        User user = findUserByUsername(username);
        String rawPassword = userRequestDto.getPassword();
        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 오류입니다.");
        }
        return user;
    }

    @Override
    public ApiResponseDto logout() {
        return null;
    }

    @Override
    public ProfileResponseDto getProfile(String username) {
        User user = findUserByUsername(username);
        return new ProfileResponseDto(user);
    }

    @Override
    @Transactional
    public ApiResponseDto updateProfile(ProfileRequestDto profileRequestDto, User loginUser) {
        User user = findUserById(loginUser.getId());
        user.setIntroduction(profileRequestDto.getIntroduction());
        return new ApiResponseDto("프로필 수정 성공", HttpStatus.OK.value());
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 이메일?입니다.")
        );
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 이메일입니다.")
        );
    }
}
