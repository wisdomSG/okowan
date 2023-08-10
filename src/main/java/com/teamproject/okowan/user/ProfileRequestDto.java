package com.teamproject.okowan.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.teamproject.okowan.user.UserRegularExpression.NICKNAME_REGEX;

@Getter
public class ProfileRequestDto {
    @Pattern(regexp = NICKNAME_REGEX,
            message = "닉네임은 영문과 숫자만 사용 가능하고 20자 미만이어야 합니다. ")
    private String nickname;
    private String introduction;
    private String address;
}
