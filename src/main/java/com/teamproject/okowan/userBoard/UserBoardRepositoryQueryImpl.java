package com.teamproject.okowan.userBoard;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.teamproject.okowan.entity.BoardRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserBoardRepositoryQueryImpl implements UserBoardRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<BoardRoleEnum> getRoleFindByUserId(Long userId, Long boardId) {
        QUserBoard userBoard = QUserBoard.userBoard;

        return Optional.ofNullable(jpaQueryFactory
                .select(userBoard.role)
                .from(userBoard)
                .where(userBoard.user.id.eq(userId).and(userBoard.board.boardId.eq(boardId)))
                .fetchOne());
    }
}
