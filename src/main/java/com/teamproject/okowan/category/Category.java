package com.teamproject.okowan.category;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "categories")
public class Category extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
