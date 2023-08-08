package com.teamproject.okowan.board;

import com.teamproject.okowan.entity.BoardRoleEnum;
import lombok.Getter;

@Getter
public class BoardInviteRequestDto {

    private Long userId;
    private BoardRoleEnum role;
}
