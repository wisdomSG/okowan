package com.teamproject.okowan.board;

import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.entity.ColorEnum;
import com.teamproject.okowan.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "boards")
public class Board extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long board_id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ColorEnum color;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<Card> cardList = new ArrayList<>();

    @Builder
    public Board(String title, String description, ColorEnum color) {
        this.title = title;
        this.description = description;
        this.color = color;
    }
}
