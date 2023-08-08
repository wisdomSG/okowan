package com.teamproject.okowan.userBoard;

import com.teamproject.okowan.entity.BoardRoleEnum;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserBoardRepositoryQuery {
    Optional<BoardRoleEnum> getRoleFindByUserId(Long userId, Long boardId);
}