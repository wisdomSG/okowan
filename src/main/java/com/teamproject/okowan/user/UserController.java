package com.teamproject.okowan.user;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/okw/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto,
                                                 BindingResult bindingResult) {
        ApiResponseDto apiResponseDto = userService.signup(signupRequestDto);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        ApiResponseDto apiResponseDto = userService.login(loginRequestDto, response);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponseDto> logout(HttpServletRequest request, HttpServletResponse response) {
        ApiResponseDto apiResponseDto = userService.logout(request, response);
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ProfileResponseDto> getProfile(@PathVariable Long userId) {
        ProfileResponseDto profileResponseDto = userService.getProfile(userId);
        return ResponseEntity.ok().body(profileResponseDto);
    }

    @PutMapping("/profile/{userId}")
    public ResponseEntity<ApiResponseDto> updateProfile(@Valid @RequestBody ProfileRequestDto profileRequestDto,
                                                        BindingResult bindingResult, @PathVariable Long userId,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = userService.updateProfile(profileRequestDto, userId, userDetails.getUser());
        return ResponseEntity.ok().body(apiResponseDto);
    }
}
