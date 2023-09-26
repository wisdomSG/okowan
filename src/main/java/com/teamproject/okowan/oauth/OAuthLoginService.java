package com.teamproject.okowan.oauth;

import com.teamproject.okowan.jwt.JwtUtil;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.user.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j(topic = "OAuth")
@Service
@RequiredArgsConstructor
public class OAuthLoginService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final RequestOAuthInfoService requestOAuthInfoService;
    private final PasswordEncoder passwordEncoder;

    private final String DEFAULT_INTRODUCTION = "안녕하세요!";

    public String login(OAuthLoginParams params, HttpServletResponse response) {
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        User user = findOrCreateUser(oAuthInfoResponse);
        String accessToken = jwtUtil.createAccessToken(user.getUsername());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);

        return accessToken;
    }

    private User findOrCreateUser(OAuthInfoResponse oAuthInfoResponse) {
        Optional<User> user = userRepository.findByUsername(oAuthInfoResponse.getEmail());
        return user.isPresent() ?
                user.get() :
                newUser(oAuthInfoResponse);
    }

    private User newUser(OAuthInfoResponse oAuthInfoResponse) {
        User user = User.builder()
                .username(oAuthInfoResponse.getEmail())
                .nickname(oAuthInfoResponse.getNickname())
                .password(passwordEncoder.encode(UUID.randomUUID().toString()))
                .introduction(DEFAULT_INTRODUCTION)
                .oAuthProvider(oAuthInfoResponse.getOAuthProvider())
                .build();

        return userRepository.save(user);

    }
}
