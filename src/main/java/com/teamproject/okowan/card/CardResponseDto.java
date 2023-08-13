package com.teamproject.okowan.card;

import com.teamproject.okowan.awsS3.S3File;
import com.teamproject.okowan.entity.ColorEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
public class CardResponseDto {
    private Long cardId;
    private String title;
    private String description;
    private ColorEnum color;
    private String deadline; // 문자열로 저장하기 위한 필드
    private List<S3File> fileList;

    public CardResponseDto(Card card) {
        this.cardId = card.getCardId();
        this.title = card.getTitle();
        this.description = card.getDescription();
        this.color = card.getColor();

        // LocalDateTime을 "yyyy-MM-dd HH:mm" 형식의 문자열로 변환하여 저장
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00");
        this.deadline = card.getDeadline().format(formatter); // deadline을 문자열로 변환하여 저장

        this.fileList = card.getS3FileList();
    }
}
