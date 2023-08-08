package com.teamproject.okowan.userBoard;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.entity.BoardRoleEnum;
import com.teamproject.okowan.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "userBoards")
public class UserBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_board_id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private BoardRoleEnum role;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public UserBoard(BoardRoleEnum role, User user, Board board) {
        this.role = role;
        this.user = user;
        this.board = board;
    }
}
