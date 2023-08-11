package com.teamproject.okowan.oauth.kakao;

import com.teamproject.okowan.oauth.OAuthLoginParams;
import com.teamproject.okowan.oauth.OAuthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@NoArgsConstructor
public class KakaoLoginParams implements OAuthLoginParams {
    //private String authorizationCode;
    private String authorization_code;

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public MultiValueMap<String, String> makeBody() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("code", authorization_code);
        return body;
    }

    @Override
    public void setCode(String code) {
        this.authorization_code = code;
    }
}
