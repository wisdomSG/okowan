package com.teamproject.okowan.category;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.common.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Long orderStand;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private List<Card> cardList = new ArrayList<>();

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

    public void setOrderStand(Long orderStand) {
        this.orderStand = orderStand;
    }

}
