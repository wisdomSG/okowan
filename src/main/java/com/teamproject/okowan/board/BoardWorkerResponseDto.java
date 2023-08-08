package com.teamproject.okowan.board;

import com.teamproject.okowan.entity.BoardRoleEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardWorkerResponseDto {
    private String username;
    private BoardRoleEnum role;

}
