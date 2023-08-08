package com.teamproject.okowan.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponseDto {
    private String nickname;
    private String introduction;
    private String address;

    ProfileResponseDto(User user) {
        this.nickname = user.getNickname();
        this.introduction = user.getIntroduction();
        this.address = user.getAddress();
    }
}
