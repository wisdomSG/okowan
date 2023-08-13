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

import java.util.List;

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

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponseDto> getProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        ProfileResponseDto profileResponseDto = userService.getProfile(userId);
        return ResponseEntity.ok().body(profileResponseDto);
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponseDto> updateProfile(@Valid @RequestBody ProfileRequestDto profileRequestDto,
                                                        BindingResult bindingResult,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        ApiResponseDto apiResponseDto = userService.updateProfile(profileRequestDto, userId, userDetails.getUser());
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponseDto> updatePassword(@Valid @RequestBody PasswordRequestDto passwordRequestDto,
                                                         BindingResult bindingResult,
                                                         @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ApiResponseDto apiResponseDto = userService.updatePassword(passwordRequestDto, userDetails.getUser());
        return ResponseEntity.ok().body(apiResponseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponseDto>> searchUser(@RequestParam String keyword, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<UserSearchResponseDto> userLists = userService.searchUsers(keyword);
        return ResponseEntity.ok().body(userLists);
    }
}
