package com.teamproject.okowan.board;

import com.teamproject.okowan.card.Card;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.entity.ColorEnum;
import com.teamproject.okowan.entity.TimeStamped;
import com.teamproject.okowan.userBoard.UserBoard;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor // 기본생성자
@Table(name = "boards")
public class Board extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ColorEnum color;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    //@OrderBy("orderPosition asc")
    private List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Card> cardList = new ArrayList<>();

    @OneToMany (mappedBy = "board",  cascade = CascadeType.REMOVE)
    private List<UserBoard> userBoardList = new ArrayList<>();
    // User와 연관되어 있으므로 보드 삭제 시 함게 삭제되는 것 필요

    @Builder
    public Board(String title, String description, ColorEnum color) {
        this.title = title;
        this.description = description;
        this.color = color;
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
//    public BoardRequestDto
}
