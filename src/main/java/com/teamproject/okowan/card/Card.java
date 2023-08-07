package com.teamproject.okowan.card;

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
}
