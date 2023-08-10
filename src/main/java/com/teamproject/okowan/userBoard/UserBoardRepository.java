package com.teamproject.okowan.userBoard;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBoardRepository extends JpaRepository<UserBoard, Long>, UserBoardRepositoryQuery {
    Optional<UserBoard> findByBoardAndUserAndRole(Board board, User user, BoardRoleEnum role);
    Optional<UserBoard> findByBoardAndUser(Board board, User inviteToUser);
}
