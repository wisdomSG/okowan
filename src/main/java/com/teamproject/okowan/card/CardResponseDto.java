package com.teamproject.okowan.card;

import com.teamproject.okowan.category.CategoryResponseDto;
import com.teamproject.okowan.comment.CommentResponseDto;
import com.teamproject.okowan.common.ColorEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CardResponseDto {
    private Long cardId;
    private String title;
    private String description;
    private ColorEnum color;
    private LocalDateTime deadline; // 문자열로 저장하기 위한 필드
    private Long categoryId;
    private Long boardId;
    private Long workerId;
    private List<CategoryResponseDto> categoryResponseDtoList;
    private List<S3FileResponseDto> fileList;
    private List<CommentResponseDto> commentResponseDtoList;

    public CardResponseDto(Card card) {
        this.cardId = card.getCardId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.color = card.getColor();
        this.categoryId = card.getCategory().getCategoryId();
        this.categoryResponseDtoList = card.getBoard().getCategoryList()
                .stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
        this.deadline = card.getDeadline();
        this.boardId = card.getBoard().getBoardId();
        this.workerId = card.getUser().getId();
        this.fileList = card.getS3FileList()
                .stream()
                .map(S3FileResponseDto::new)
                .collect(Collectors.toList());
        this.commentResponseDtoList = card.getCommentList()
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

    }
}
