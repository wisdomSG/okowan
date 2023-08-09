package com.teamproject.okowan.userBoard;

import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserBoardRepositoryQuery {
    Optional<BoardRoleEnum> getRoleFindByUserIdAndBoardId(Long userId, Long boardId);

    List<UserBoard> getAllFindByBoardId(Long boardId);
}