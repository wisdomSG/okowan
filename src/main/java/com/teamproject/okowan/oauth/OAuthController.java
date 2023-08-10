package com.teamproject.okowan.oauth;

import com.teamproject.okowan.aop.ApiResponseDto;
import com.teamproject.okowan.oauth.kakao.KakaoLoginParams;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/okw/oauths")
public class OAuthController {
    private final OAuthLoginService oAuthLoginService;

    /*
        Get AccessToken : https://kauth.kakao.com/oauth/authorize?client_id=3c3ddd336b4f3c87d8b029ee2a1d2185&redirect_uri=http://localhost:8080/okw/oauths/callback/kakao&response_type=code
        Redirect URI : http://localhost:8080/okw/oauths/callback/kakao?code={Authorization Code}
     */
    @GetMapping("/callback/kakao")
    public ResponseEntity<ApiResponseDto> loginKakao(@RequestBody KakaoLoginParams params, HttpServletResponse response) {
        return ResponseEntity.ok().body(oAuthLoginService.login(params,response));
    }

}
