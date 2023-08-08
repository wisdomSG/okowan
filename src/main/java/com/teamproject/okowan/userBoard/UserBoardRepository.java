package com.teamproject.okowan.userBoard;

import com.teamproject.okowan.entity.BoardRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserBoardRepository extends JpaRepository<UserBoard, Long> {

    Optional<UserBoard> findByUserIdAndBoardIdAndRole(Long userId, Long boardId, BoardRoleEnum role);

}
