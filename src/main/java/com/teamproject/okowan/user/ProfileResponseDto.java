package com.teamproject.okowan.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {
    private String username;
    private String introduction;

    ProfileResponseDto(User user) {
        this.username = user.getUsername();
        this.introduction = user.getIntroduction();
    }
}
