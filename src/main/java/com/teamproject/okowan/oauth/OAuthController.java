package com.teamproject.okowan.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamproject.okowan.jwt.JwtUtil;
import com.teamproject.okowan.oauth.kakao.KakaoLoginParams;
import com.teamproject.okowan.security.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/okw/oauths")
public class OAuthController {
    private final OAuthLoginService oAuthLoginService;

    /*
        Get AccessToken : https://kauth.kakao.com/oauth/authorize?client_id={RESTAPI Key}&redirect_uri=http://localhost:8080/okw/oauths/callback/kakao&response_type=code
        Redirect URI : http://localhost:8080/okw/oauths/callback/kakao?code={Authorization Code}
     */
    @GetMapping("/callback/kakao")
    public String loginKakao(@RequestParam String code, HttpServletResponse response) throws UnsupportedEncodingException {
        KakaoLoginParams kakaoLoginParams = new KakaoLoginParams();
        kakaoLoginParams.setCode(code);
        String token = oAuthLoginService.login(kakaoLoginParams,response);

        token = URLEncoder.encode(token,"utf-8").replaceAll("\\+", "%20");
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token);
        cookie.setPath("/");
        response.addCookie(cookie); //브라우저 쿠키에 jwt 토큰 생성

        return "redirect:/";
    }

}
