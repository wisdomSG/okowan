package com.teamproject.okowan.oauth;

public enum OAuthProvider {
    ORIGIN(OAuth.ORIGIN),
    KAKAO(OAuth.KAKAO),
    NAVER(OAuth.NAVER);

    private final String provider;

    OAuthProvider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return this.provider;
    }

    public static class OAuth {
        public static final String ORIGIN = "OKOWAN";
        public static final String KAKAO = "KAKAO";
        public static final String NAVER = "NAVER";
    }
}
