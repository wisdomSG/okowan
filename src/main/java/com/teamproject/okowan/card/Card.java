package com.teamproject.okowan.card;

import com.teamproject.okowan.awsS3.S3File;
import com.teamproject.okowan.board.Board;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.comment.Comment;
import com.teamproject.okowan.entity.ColorEnum;
import com.teamproject.okowan.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false, unique = true)
    private String title;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private ColorEnum color;

    private LocalDateTime deadline; // 나중에 다시 처리 예정

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "card", cascade = CascadeType.REMOVE)
    private List<S3File> s3FileList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Card(String title, Category category, User user) {
        this.title = title;
        this.category = category;
        this.user = user;
    }

    public Card(String title, String description, ColorEnum color, LocalDateTime deadline, Category category, User user) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.deadline = deadline;
        this.category = category;
        this.user = user;
        this.board = category.getBoard();
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

    public void setUser(User user) {
        this.user = user;
    }
}
