package com.teamproject.okowan.board;

import com.teamproject.okowan.entity.BoardRoleEnum;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import static com.teamproject.okowan.user.UserRegularExpression.EMAIL_REGEX;

@Getter
public class BoardInvitationRequestDto {

    private Long BoardId;

    @Pattern(regexp = EMAIL_REGEX,
            message = "아이디는 이메일 형식이어야 합니다. ")
    private String username;

    private BoardRoleEnum role;
}