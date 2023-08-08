package com.teamproject.okowan.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.teamproject.okowan.user.UserRegularExpression.*;

@Getter
public class SignupRequestDto {


    @Pattern(regexp = EMAIL_REGEX,
            message = "아이디는 이메일 형식이어야 합니다. ")
    private String username;

    @Pattern(regexp = PASSWORD_REGEX,
            message = "비밀번호는 4자 이상 20자 미만이어야 합니다. ")
    private String password;

    @Pattern(regexp = NICKNAME_REGEX,
            message = "닉네임은 영문과 숫자만 사용 가능하고 20자 미만이어야 합니다.")
    private String nickname;
}
