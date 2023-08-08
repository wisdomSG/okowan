package com.teamproject.okowan.userBoard;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {
    Optional<UserBoard> findByBoardAndUserAndRole(Board board, User user, BoardRoleEnum role);

    List<UserBoard> findAllByBoard(Board board);
}
