package com.teamproject.okowan.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchResponseDto {
    private String nickname;
    private String username;
    private String introduce;

    public UserSearchResponseDto(User user) {
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.introduce = user.getIntroduction();
    }
}
