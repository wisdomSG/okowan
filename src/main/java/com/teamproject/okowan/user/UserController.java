package com.teamproject.okowan.user;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.jwt.JwtUtil;
import com.teamproject.okowan.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/okw/user")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
        ApiResponseDto apiResponseDto = userService.signup(userRequestDto);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody UserRequestDto userRequestDto, HttpServletResponse response) {
        ApiResponseDto apiResponseDto = userService.login(userRequestDto, response);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout() {
        ApiResponseDto apiResponseDto = userService.logout();
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable String username) {
        ProfileResponseDto profileResponseDto = userService.getProfile(username);
        return ResponseEntity.ok().body(profileResponseDto);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> updateProfile(@RequestBody ProfileRequestDto profileRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = userService.updateProfile(profileRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(apiResponseDto);
    }
}
