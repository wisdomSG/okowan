package com.teamproject.okowan.card;

import com.teamproject.okowan.awsS3.S3File;
import com.teamproject.okowan.category.Category;
import com.teamproject.okowan.category.CategoryResponseDto;
import com.teamproject.okowan.entity.ColorEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private List<CategoryResponseDto> categoryResponseDtoList;
    private List<S3File> fileList;

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
//        // LocalDateTime을 "yyyy-MM-dd HH:mm" 형식의 문자열로 변환하여 저장
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
//        if(card.getDeadline() != null) {
//            this.deadline = card.getDeadline().format(formatter);
//        } else {
//            this.deadline = "";
//        }

        this.fileList = card.getS3FileList();
    }
}
