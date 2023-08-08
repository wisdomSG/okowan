package com.teamproject.okowan.category;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
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

    @Column
    @GeneratedValue
    private Long orderStand;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public Category(CategoryRequestDto categoryRequestDto) {
        this.title = categoryRequestDto.getTitle();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
