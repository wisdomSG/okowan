package com.teamproject.okowan.board;

import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import com.teamproject.okowan.userBoard.UserBoard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardWorkerResponseDto {
    private Long userId;
    private String username;
    private String nickname;
    private BoardRoleEnum role;

    public BoardWorkerResponseDto(UserBoard userBoard) {
        this.userId = userBoard.getUser().getId();
        this.username = userBoard.getUser().getUsername();
        this.nickname = userBoard.getUser().getNickname();
        this.role = userBoard.getRole();
    }

}
