package com.teamproject.okowan.card;

import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.entity.ColorEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long card_id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ColorEnum color;

    private LocalDateTime deadline; // 나중에 다시 처리 예정

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public Card(String title, String description, ColorEnum color, LocalDateTime deadline, Category category) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.deadline = deadline;
        this.category = category;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
