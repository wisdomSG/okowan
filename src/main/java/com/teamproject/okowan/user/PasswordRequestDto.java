package com.teamproject.okowan.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.teamproject.okowan.user.UserRegularExpression.PASSWORD_REGEX;

@Getter
public class PasswordRequestDto {
    private String currentPassword;
    @Pattern(regexp = PASSWORD_REGEX,
            message = "비밀번호는 4자 이상 20자 미만이어야 합니다. ")
    private String newPassword;
    private String confirmNewPassword;
}
