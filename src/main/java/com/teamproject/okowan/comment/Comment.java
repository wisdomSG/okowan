package com.teamproject.okowan.comment;

import com.teamproject.okowan.card.Card;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
