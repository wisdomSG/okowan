package com.teamproject.okowan.user;

import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private final String username;
    private final String introduction;

    ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
    }
}
